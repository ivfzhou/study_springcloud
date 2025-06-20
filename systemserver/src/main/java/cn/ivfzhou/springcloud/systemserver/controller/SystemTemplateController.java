package cn.ivfzhou.springcloud.systemserver.controller;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.CouponTemplate;
import cn.ivfzhou.springcloud.feign.CouponFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemTemplateController {

    @Autowired
    private CouponFeign dicFeign;

    /**
     * 查询模板列表
     */
    @RequestMapping("/templatelist")
    public String templateList(Model model) {
        //请求coupon服务
        ResultData<List<CouponTemplate>> templates = dicFeign.getTemplates();
        model.addAttribute("templates", templates.getData());
        return "templatelist";
    }

    /**
     * 跳转到模板添加的页面
     */
    @RequestMapping("/toTemplateAdd")
    public String toTemplateAdd() {
        return "templateadd";
    }

    /**
     * 新增模板
     */
    @RequestMapping("/templateAdd")
    public String templateAdd(CouponTemplate couponTemplate) {

        //保存模板
        dicFeign.insertTemplate(couponTemplate);

        return "redirect:/system/templatelist";
    }

}
