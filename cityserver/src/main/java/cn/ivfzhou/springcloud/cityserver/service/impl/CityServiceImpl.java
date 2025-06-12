package cn.ivfzhou.springcloud.cityserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ivfzhou.springcloud.cityserver.dao.CityMapper;
import cn.ivfzhou.springcloud.cityserver.service.ICityService;
import cn.ivfzhou.springcloud.common.util.PinyinUtil;
import cn.ivfzhou.springcloud.entity.db.City;

@Slf4j
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public boolean save(City city) {
        // 校验参数。
        if (city == null) return false;

        log.info("city: {}", city);

        // 设置城市的拼音。
        String pinyin = PinyinUtil.str2Pinyin(city.getName());
        city.setPinyin(pinyin);
        return super.save(city);
    }

    /**
     * 增减城市的酒店数量。
     */
    @Override
    @Transactional
    public int updateCityNumber(Integer cityId, Integer number) {
        // 校验参数。
        if (cityId == null || cityId <= 0 || number == null || number <= 0) {
            return 0;
        }

        log.info("cityId: {}, number: {}", cityId, number);

        return cityMapper.updateCityNumber(cityId, number);
    }

}
