package cn.ivfzhou.springcloud.couponserver.service.impl;

import cn.ivfzhou.springcloud.couponserver.dao.DictionaryContentMapper;
import cn.ivfzhou.springcloud.couponserver.service.IDictionaryContentService;
import cn.ivfzhou.springcloud.entity.db.DictionaryContent;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DictionaryContentServiceImpl extends ServiceImpl<DictionaryContentMapper, DictionaryContent> implements IDictionaryContentService {
}
