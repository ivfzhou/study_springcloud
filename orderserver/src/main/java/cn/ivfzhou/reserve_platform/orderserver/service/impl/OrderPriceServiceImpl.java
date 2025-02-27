package cn.ivfzhou.reserve_platform.orderserver.service.impl;

import cn.ivfzhou.reserve_platform.orderserver.dao.OrderPriceMapper;
import cn.ivfzhou.reserve_platform.entity.db.OrderPriceDetail;
import cn.ivfzhou.reserve_platform.orderserver.service.IOrderPriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderPriceServiceImpl extends ServiceImpl<OrderPriceMapper, OrderPriceDetail> implements IOrderPriceService {
}
