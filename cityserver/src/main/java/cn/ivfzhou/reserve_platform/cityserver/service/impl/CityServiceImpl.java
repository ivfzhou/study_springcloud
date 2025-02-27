package cn.ivfzhou.reserve_platform.cityserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ivfzhou.reserve_platform.cityserver.dao.CityMapper;
import cn.ivfzhou.reserve_platform.entity.db.City;
import cn.ivfzhou.reserve_platform.cityserver.service.ICityService;
import cn.ivfzhou.reserve_platform.common.util.PinyinUtil;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public boolean save(City entity) {
        // 设置城市的拼音
        String pinyin = PinyinUtil.str2Pinyin(entity.getName());
        entity.setPinyin(pinyin);
        return super.save(entity);
    }

    /**
     * 修改对应城市的酒店数量
     *
     * @return 线程1 -> 开启事务 -> 加锁 -> 业务 -> 释放锁 -> 提交事务
     * 线程2 -> 开启事务 -> 加锁 -> 业务
     */
    @Override
    @Transactional
    public int updateCityNumber(Integer cid, Integer number) {
        return cityMapper.updateCityNumber(cid, number);
    }

}
