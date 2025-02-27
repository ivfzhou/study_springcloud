package cn.ivfzhou.reserve_platform.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.City;
import cn.ivfzhou.reserve_platform.entity.ResultData;

@FeignClient("micro-city")
public interface CityFeign {

    @RequestMapping("/city/save")
    ResultData<Boolean> citySave(@RequestBody City city);

    @RequestMapping("/city/list")
    ResultData<List<City>> cityList();

    @RequestMapping("/city/update/{cid}/{number}")
    ResultData<Boolean> updateCityHNumber(@PathVariable("cid") Integer cid, @PathVariable("number") Integer number);

    @RequestMapping("/city/queryName/{cid}")
    ResultData<City> queryName(@PathVariable("cid") Integer cid);

}
