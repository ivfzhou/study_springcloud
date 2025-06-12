package cn.ivfzhou.springcloud.couponserver.service.impl;

import cn.ivfzhou.springcloud.couponserver.dao.DictionaryMapper;
import cn.ivfzhou.springcloud.couponserver.service.IDictionaryContentService;
import cn.ivfzhou.springcloud.couponserver.service.IDictionaryService;
import cn.ivfzhou.springcloud.entity.db.DictionaryContent;
import cn.ivfzhou.springcloud.entity.db.Dictionary;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Autowired
    private IDictionaryContentService dicContentService;

    @Override
    public List<Dictionary> list() {

        List<Dictionary> dics = super.list();
        // 循环字典集合，查询字典内容。
        dics.forEach(dictionaries -> {
            List<DictionaryContent> contents = dicContentService.list(new QueryWrapper<DictionaryContent>().eq("did", dictionaries.getId()));
            dictionaries.setContents(contents);
        });

        return dics;
    }
}
