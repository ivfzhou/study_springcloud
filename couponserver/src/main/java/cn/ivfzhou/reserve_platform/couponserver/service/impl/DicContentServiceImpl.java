package cn.ivfzhou.reserve_platform.couponserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import cn.ivfzhou.reserve_platform.couponserver.dao.DicContentMapper;
import cn.ivfzhou.reserve_platform.entity.db.DictionariesContent;
import cn.ivfzhou.reserve_platform.couponserver.service.IDicContentService;

@Service
public class DicContentServiceImpl extends ServiceImpl<DicContentMapper, DictionariesContent> implements IDicContentService {
}
