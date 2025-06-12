package cn.ivfzhou.springcloud.systemserver.controller;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.DictionaryContent;
import cn.ivfzhou.springcloud.entity.db.Dictionary;
import cn.ivfzhou.springcloud.feign.CouponFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemDicController {

    @Autowired
    private CouponFeign dicFeign;

    /**
     * 字典列表
     */
    @RequestMapping("/diclist")
    public String dicList(Model model) {
        //调用优惠卷服务
        ResultData<List<Dictionary>> list = dicFeign.list();
        //展示字典
        model.addAttribute("diclist", list.getData());

        return "diclist";
    }

    /**
     * 跳转到字典添加的页面
     */
    @RequestMapping("/todicadd")
    public String toDicAdd() {
        return "dicadd";
    }

    /**
     * 跳转到字典内容添加页面
     */
    @RequestMapping("/todiccontentadd")
    public String toDicContentAdd() {
        return "diccontentadd";
    }

    /**
     * 新增字典
     */
    @RequestMapping("/dicinsert")
    public String dicInsert(Dictionary dictionary) {
        dicFeign.insertDic(dictionary);
        return "redirect:/system/diclist";
    }

    /**
     * 新增字典内容
     */
    @RequestMapping("/diccontentinsert")
    public String dicContentInsert(DictionaryContent dictionaryContent) {
        dicFeign.insertDicContent(dictionaryContent);
        return "redirect:/system/diclist";
    }

}
