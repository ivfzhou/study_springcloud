package cn.ivfzhou.reserve_platform.couponserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.CouponTemplate;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.couponserver.service.ICouponTemplateService;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private ICouponTemplateService couponTemplateService;

    /**
     * 模板列表
     */
    @CrossOrigin
    @RequestMapping("/list")
    public ResultData<List<CouponTemplate>> getTemplates() {
        List<CouponTemplate> templates = couponTemplateService.list();
        return new ResultData<List<CouponTemplate>>().setData(templates);
    }

    /**
     * 保存模板对象
     */
    @RequestMapping("/insert")
    public ResultData<Boolean> insertTemplate(@RequestBody CouponTemplate couponTemplate) {
        boolean flag = couponTemplateService.save(couponTemplate);
        return new ResultData<Boolean>().setData(flag);
    }

    /**
     * 获得指定类型的模板集合
     */
    @RequestMapping("/listByType")
    public ResultData<List<CouponTemplate>> getTemplatesByType(@RequestParam Integer type) {
        List<CouponTemplate> templates = couponTemplateService.list(new QueryWrapper<CouponTemplate>().eq("template_type", type));
        return new ResultData<List<CouponTemplate>>().setData(templates);
    }
}
