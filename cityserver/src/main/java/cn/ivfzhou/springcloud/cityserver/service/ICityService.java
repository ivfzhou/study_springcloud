package cn.ivfzhou.springcloud.cityserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.City;

public interface ICityService extends IService<City> {

    /*
     * 增减城市的酒店数量。
     * */
    int updateCityNumber(Integer cityId, Integer number);

}
