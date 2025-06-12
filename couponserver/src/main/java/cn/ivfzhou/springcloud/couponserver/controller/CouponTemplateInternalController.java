package cn.ivfzhou.springcloud.couponserver.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.springcloud.couponserver.service.ICouponTemplateService;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.CouponTemplate;

@RestController
@RequestMapping("/internal/coupon/template")
public class CouponTemplateInternalController {

    @Autowired
    private ICouponTemplateService couponTemplateService;

    /**
     * 模板列表。
     */
    @CrossOrigin
    @RequestMapping("/list")
    public ResultData<List<CouponTemplate>> getTemplates() {
        return new ResultData<List<CouponTemplate>>().setData(couponTemplateService.list());
    }

    /**
     * 保存模板对象
     */
    @RequestMapping("/insert")
    public ResultData<Boolean> insertTemplate(@RequestBody CouponTemplate couponTemplate) {
        return new ResultData<Boolean>().setData(couponTemplateService.save(couponTemplate));
    }

    /**
     * 获得指定类型的模板集合。
     */
    @RequestMapping("/listByType")
    public ResultData<List<CouponTemplate>> getTemplatesByType(@RequestParam Integer type) {
        return new ResultData<List<CouponTemplate>>().setData(
                couponTemplateService.list(new QueryWrapper<CouponTemplate>().eq("type", type)));
    }
}
