package cn.ivfzhou.springcloud.cityserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ivfzhou.springcloud.entity.db.City;

/**
 * 城市服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供城市表的基础业务操作，
 * 并扩展了更新城市酒店数量的自定义方法。
 * </p>
 */
public interface ICityService extends IService<City> {

    /*
     * 增减城市的酒店数量。
     * */
    int updateCityNumber(Integer cityId, Integer number);

}
