package cn.ivfzhou.springcloud.feign;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.City;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 城市服务 Feign 远程调用接口。
 * <p>
 * 用于其他微服务通过 OpenFeign 调用城市服务（micro-city）的内部接口，
 * 实现城市信息的增删改查操作。
 * </p>
 */
@FeignClient("micro-city")
public interface CityFeign {

    /** 新增或更新城市信息 */
    @RequestMapping("/city/save")
    ResultData<Boolean> citySave(@RequestBody City city);

    /** 查询所有城市列表 */
    @RequestMapping("/city/list")
    ResultData<List<City>> cityList();

    /** 更新城市的酒店数量 */
    @RequestMapping("/city/update/{cid}/{number}")
    ResultData<Boolean> updateCityHNumber(@PathVariable("cid") Integer cid, @PathVariable("number") Integer number);

    /** 根据城市ID查询城市信息 */
    @RequestMapping("/city/queryName/{cid}")
    ResultData<City> queryName(@PathVariable("cid") Integer cid);

}
