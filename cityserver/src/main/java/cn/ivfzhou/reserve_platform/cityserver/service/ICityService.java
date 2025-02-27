package cn.ivfzhou.reserve_platform.cityserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.reserve_platform.entity.db.City;

public interface ICityService extends IService<City> {

    int updateCityNumber(Integer cid, Integer number);

}
