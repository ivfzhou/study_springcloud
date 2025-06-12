package cn.ivfzhou.springcloud.couponserver.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.springcloud.couponserver.service.IDictionaryContentService;
import cn.ivfzhou.springcloud.couponserver.service.IDictionaryService;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Dictionary;
import cn.ivfzhou.springcloud.entity.db.DictionaryContent;

@RestController
@RequestMapping("/internal/dictionary")
public class DictionaryInternalController {

    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private IDictionaryContentService dictionaryContentService;

    /**
     * 返回字典列表。
     */
    @RequestMapping("/list")
    @CrossOrigin
    public ResultData<List<Dictionary>> list() {
        return new ResultData<List<Dictionary>>().setData(dictionaryService.list());
    }


    /**
     * 查询字典数据，根据字典 Id。
     */
    @CrossOrigin
    @RequestMapping("/getContentByDid")
    public ResultData<List<DictionaryContent>> getContentByDid(@RequestParam Integer dictionaryId) {
        return new ResultData<List<DictionaryContent>>().setData(
                dictionaryContentService.list(new QueryWrapper<DictionaryContent>().eq("dictionary_id", dictionaryId)));
    }

    /**
     * 新增字典。
     */
    @RequestMapping("/insert")
    public ResultData<Boolean> insertDic(@RequestBody Dictionary dictionary) {
        return new ResultData<Boolean>().setData(dictionaryService.save(dictionary));
    }

    /**
     * 新增字典内容。
     */
    @RequestMapping("/insertContent")
    public ResultData<Boolean> insertDicContent(@RequestBody DictionaryContent content) {
        return new ResultData<Boolean>().setData(dictionaryContentService.save(content));
    }

}
