package cn.ivfzhou.reserve_platform.couponserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import cn.ivfzhou.reserve_platform.couponserver.dao.CouponTemplateMapper;
import cn.ivfzhou.reserve_platform.entity.db.CouponTemplate;
import cn.ivfzhou.reserve_platform.couponserver.service.ICouponTemplateService;

@Service
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate> implements ICouponTemplateService {
}
