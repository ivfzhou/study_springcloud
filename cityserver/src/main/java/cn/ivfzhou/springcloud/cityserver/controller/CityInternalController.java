package cn.ivfzhou.springcloud.cityserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.springcloud.cityserver.service.ICityService;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.City;

@RestController
@RequestMapping("/internal/city")
@Slf4j
public class CityInternalController {

    @Autowired
    private ICityService cityService;

    /**
     * 添加城市接口。
     */
    @PutMapping("/add")
    public ResultData<Boolean> add(@RequestBody City city) {
        return new ResultData<Boolean>().setData(cityService.save(city));
    }

    /**
     * 修改城市对应的酒店数量。
     */
    @PostMapping("/update/{cityId}/{number}")
    public ResultData<Boolean> updateCityHotelNumber(@PathVariable Integer cityId, @PathVariable Integer number) {
        return new ResultData<Boolean>().setData(cityService.updateCityNumber(cityId, number) > 0);
    }

}
