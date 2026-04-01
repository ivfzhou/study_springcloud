package cn.ivfzhou.springcloud.cityserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import cn.ivfzhou.springcloud.entity.db.City;

/**
 * 城市数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供城市表（t_city）的基础 CRUD 操作，
 * 并自定义了更新城市酒店数量的方法。
 * </p>
 */
// @org.apache.ibatis.annotations.Mapper
public interface CityMapper extends BaseMapper<City> {

    /*
     * 增减城市的酒店数量。
     * */
    int updateCityNumber(@Param("cityId") int cityId, @Param("number") int number);

}
