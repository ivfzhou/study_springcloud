package cn.ivfzhou.springcloud.cityserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import cn.ivfzhou.springcloud.entity.db.City;

// @org.apache.ibatis.annotations.Mapper
public interface CityMapper extends BaseMapper<City> {

    /*
     * 增减城市的酒店数量。
     * */
    int updateCityNumber(@Param("cityId") int cityId, @Param("number") int number);

}
