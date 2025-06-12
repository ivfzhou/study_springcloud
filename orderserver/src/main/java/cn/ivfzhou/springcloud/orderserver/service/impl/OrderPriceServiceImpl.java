package cn.ivfzhou.springcloud.orderserver.service.impl;

import cn.ivfzhou.springcloud.entity.db.OrderPriceDetail;
import cn.ivfzhou.springcloud.orderserver.dao.OrderPriceMapper;
import cn.ivfzhou.springcloud.orderserver.service.IOrderPriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderPriceServiceImpl extends ServiceImpl<OrderPriceMapper, OrderPriceDetail> implements IOrderPriceService {
}
