package cn.ivfzhou.reserve_platform.couponserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.Dictionary;
import cn.ivfzhou.reserve_platform.entity.db.DictionariesContent;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.couponserver.service.IDicContentService;
import cn.ivfzhou.reserve_platform.couponserver.service.IDicService;

@RestController
@RequestMapping("/dic")
public class DicController {

    @Autowired
    private IDicService dicService;

    @Autowired
    private IDicContentService dicContentService;

    /**
     * 返回字典列表
     */
    @RequestMapping("/list")
    @CrossOrigin
    public ResultData<List<Dictionary>> list() {
        List<Dictionary> list = dicService.list();
        return new ResultData<List<Dictionary>>().setData(list);
    }


    /**
     * 查询字典数据，根据字典id
     */
    @CrossOrigin
    @RequestMapping("/getContentByDid")
    public ResultData<List<DictionariesContent>> getContentByDid(@RequestParam Integer did) {
        List<DictionariesContent> contents = dicContentService.list(new QueryWrapper<DictionariesContent>().eq("did", did));
        return new ResultData<List<DictionariesContent>>().setData(contents);
    }

    /**
     * 新增字典
     */
    @RequestMapping("/insert")
    public ResultData<Boolean> insertDic(@RequestBody Dictionary dictionary) {
        boolean flag = dicService.save(dictionary);
        return new ResultData<Boolean>().setData(flag);
    }

    /**
     * 新增字典内容
     */
    @RequestMapping("/insertContent")
    public ResultData<Boolean> insertDicContent(@RequestBody DictionariesContent content) {
        boolean flag = dicContentService.save(content);
        return new ResultData<Boolean>().setData(flag);
    }

}
