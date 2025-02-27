package cn.ivfzhou.reserve_platform.couponserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ivfzhou.reserve_platform.couponserver.dao.DicMapper;
import cn.ivfzhou.reserve_platform.entity.db.Dictionary;
import cn.ivfzhou.reserve_platform.entity.db.DictionariesContent;
import cn.ivfzhou.reserve_platform.couponserver.service.IDicContentService;
import cn.ivfzhou.reserve_platform.couponserver.service.IDicService;

import java.util.List;

@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, Dictionary> implements IDicService {

    @Autowired
    private IDicContentService dicContentService;

    @Override
    public List<Dictionary> list() {

        List<Dictionary> dics = super.list();
        // 循环字典集合，查询字典内容。
        dics.forEach(dictionaries -> {
            List<DictionariesContent> contents = dicContentService.list(new QueryWrapper<DictionariesContent>().eq("did", dictionaries.getId()));
            dictionaries.setContents(contents);
        });

        return dics;
    }
}
