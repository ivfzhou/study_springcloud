package cn.ivfzhou.reserve_platform.orderserver.service.impl;

import cn.ivfzhou.reserve_platform.orderserver.dao.OrderMapper;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.event.util.EventUtil;
import cn.ivfzhou.reserve_platform.feign.CouponFeign;
import cn.ivfzhou.reserve_platform.feign.HotelFeign;
import cn.ivfzhou.reserve_platform.common.lock.LockUtil;
import cn.ivfzhou.reserve_platform.common.login.UserUtil;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.*;
import cn.ivfzhou.reserve_platform.orderserver.service.IOrderPriceService;
import cn.ivfzhou.reserve_platform.orderserver.service.IOrderService;
import cn.ivfzhou.reserve_platform.common.util.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private HotelFeign hotelFeign;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IOrderPriceService priceService;

    @Autowired
    private EventUtil eventUtil;

    @Autowired
    private LockUtil lockUtil;

    @Autowired
    private CouponFeign dicFeign;

    /**
     * 计算订单价格
     */
    @Override
    public OrderPriceResult getOrderPrice(OrderPriceParam params) {


        //获得房间信息
        ResultData<Room> rooms = hotelFeign.getRoomById(params.getRid());

        //总价
        BigDecimal allPrice = BigDecimal.valueOf(0);

        //价格明细列表
        List<List<String>> priceDetail = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //循环房间的价格列表
        for (RoomPrice roomPrice : rooms.getData().getPrices()) {
            if (roomPrice.getDate().getTime() >= params.getBeginTime().getTime() &&
                    roomPrice.getDate().getTime() < params.getEndTime().getTime()) {
                //房价单价
                BigDecimal price = roomPrice.getPrice();
                //房价累加
                allPrice = allPrice.add(price);

                //设置明细
                List<String> list = new ArrayList<>();
                list.add(simpleDateFormat.format(roomPrice.getDate()));
                list.add(params.getRnumber() + " x " + price.doubleValue());
                priceDetail.add(list);
            }
        }

        //乘以房价数量
        allPrice = allPrice.multiply(BigDecimal.valueOf(params.getRnumber()));

        //通过优惠券id 获得优惠券对象 计算优惠后的价格
        if (params.getCid() != null && params.getCid() != -1) {
            Coupon coupon = dicFeign.getCouponById(params.getCid()).getData();
            //优惠券已经查询成功， 优惠卷的规则校验
            //优惠券查到 并且 优惠券条件可用 并且 金额在优惠券的可用范围内
            if (coupon != null && coupon.getLimit().hasLimit(params) && coupon.getRule().hasPrice(allPrice.doubleValue())) {
                //可以使用优惠券
                //订单优惠后的价格
                double youhui = coupon.getRule().discount(allPrice.doubleValue());

                //该优惠券到底优惠了多少钱
                double youhuiPrice = coupon.getRule().discountPrice(allPrice.doubleValue());

                //将优惠后的价格替换成总价格
                allPrice = BigDecimal.valueOf(youhui);

                //记录优惠明细
                List<String> list = new ArrayList<>();
                list.add("使用优惠券");
                list.add("-" + youhuiPrice);
                priceDetail.add(list);
            }
        }


        OrderPriceResult priceResult = new OrderPriceResult()
                .setAllPrice(allPrice.doubleValue())
                .setPriceDetails(priceDetail);

        return priceResult;
    }

    /**
     * 下单
     */
    @Override
    @Transactional
    public String insertOrder(Order order, OrderPriceParam params) {

        lockUtil.lock("orders_" + params.getRid());

        try {
            //获得当前登录用户
            User user = UserUtil.getUser();

            //计算总价格
            OrderPriceResult orderPrice = getOrderPrice(params);

            order
                    .setOid(UUID.randomUUID().toString().replace("-", ""))
                    .setAllPrice(orderPrice.getAllPrice())
                    .setNumber(params.getRnumber())
                    .setDays(DateUtil.days(order.getBeginTime(), order.getEndTime()))
                    .setUid(user.getId());


            //保存价格详情信息
            List<OrderPriceDetail> orderPriceDetails = new ArrayList<>();
            ResultData<Room> room = hotelFeign.getRoomById(order.getRid());
            List<RoomPrice> prices = room.getData().getPrices();
            for (RoomPrice price : prices) {
                if (price.getDate().getTime() >= params.getBeginTime().getTime() &&
                        price.getDate().getTime() < params.getEndTime().getTime()) {

                    //判断下单的时间段，房间是否充足
                    if (price.getHasNumber() - price.getNumber() < params.getRnumber()) {
                        //这一天已经没房了
                        return null;
                    }

                    OrderPriceDetail orderPriceDetail = new OrderPriceDetail()
                            .setOid(order.getOid())
                            .setPrice(price.getPrice().doubleValue())
                            .setTime(price.getDate());

                    //保存到订单的价格详情列表
                    orderPriceDetails.add(orderPriceDetail);
                }
            }

            //保存订单对象
            orderMapper.insert(order);

            //保存详情
            priceService.saveBatch(orderPriceDetails);

            //修改数据库的房间剩余数量 - 酒店服务
            //修改ElasticSearch中房间的剩余数量 - 搜索服务
            eventUtil.publishEvent(EventConstant.EVENT_HOTEL_ROOM_UPDATE, params);
        } finally {
            //解锁
            lockUtil.unlock("orders_" + params.getRid());
        }

        return order.getOid();
    }

    /**
     * 修改订单状态
     */
    @Override
    @Transactional
    public int updateOrderStatus(String oid, Integer status) {
        Order order = orderMapper.selectById(oid);
        order.setStatus(status);
        return orderMapper.updateById(order);
    }

}
