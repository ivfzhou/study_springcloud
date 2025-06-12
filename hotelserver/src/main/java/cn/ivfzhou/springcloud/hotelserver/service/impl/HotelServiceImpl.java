package cn.ivfzhou.springcloud.hotelserver.service.impl;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.City;
import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.feign.CityFeign;
import cn.ivfzhou.springcloud.hotelserver.dao.HotelMapper;
import cn.ivfzhou.springcloud.hotelserver.service.IHotelService;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomService;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.rabbitmq.util.EventUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private CityFeign cityFeign;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private EventUtil eventUtil;

    /**
     * 保存酒店信息。
     */
    @Override
    public boolean save(Hotel entity) {
        boolean flag = super.save(entity);
        // 调用城市服务，修改城市对应的数量。
        cityFeign.updateCityHNumber(entity.getCid(), 1);

        // 发布事件。
        eventUtil.publishEvent(EventConstant.EVENT_HOTEL_INSERT, entity);

        return flag;
    }

    /**
     * 查询酒店列表。
     */
    @Override
    public List<Hotel> list() {
        List<Hotel> hotals = super.list();
        for (Hotel hotal : hotals) {
            // 获得酒店对应的城市 id。
            Integer cid = hotal.getCid();
            // 获得城市对象。
            ResultData<City> cityResultData = cityFeign.queryName(cid);
            // 设置城市对象。
            hotal.setCity(cityResultData.getData());

            // 获得客房信息。
            QueryWrapper<Room> wrapper = new QueryWrapper<Room>()
                    .eq("hid", hotal.getId());
            List<Room> rooms = roomService.list(wrapper);
            hotal.setRooms(rooms);
        }
        return hotals;
    }

    /**
     * 查询酒店信息 - 根据id。
     */
    @Override
    public Hotel getById(Serializable id) {
        Hotel hotal = super.getById(id);

        // 获得酒店对应的城市id。
        Integer cid = hotal.getCid();
        // 获得城市对象。
        ResultData<City> cityResultData = cityFeign.queryName(cid);
        // 设置城市对象。
        hotal.setCity(cityResultData.getData());

        // 获得客房信息。
        QueryWrapper<Room> wrapper = new QueryWrapper<Room>()
                .eq("hid", hotal.getId());
        List<Room> rooms = roomService.list(wrapper);
        hotal.setRooms(rooms);

        return hotal;
    }

}
