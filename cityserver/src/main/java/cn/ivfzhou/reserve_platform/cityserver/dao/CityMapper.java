package cn.ivfzhou.reserve_platform.cityserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import cn.ivfzhou.reserve_platform.entity.db.City;

public interface CityMapper extends BaseMapper<City> {

    int updateCityNumber(@Param("cid") Integer cid, @Param("number") Integer number);

}
