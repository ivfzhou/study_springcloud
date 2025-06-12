package cn.ivfzhou.springcloud.couponserver.service.impl;

import cn.ivfzhou.springcloud.couponserver.dao.CouponTemplateMapper;
import cn.ivfzhou.springcloud.couponserver.service.ICouponTemplateService;
import cn.ivfzhou.springcloud.entity.db.CouponTemplate;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate> implements ICouponTemplateService {
}
