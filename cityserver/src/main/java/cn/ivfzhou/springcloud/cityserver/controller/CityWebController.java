package cn.ivfzhou.springcloud.cityserver.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.springcloud.cityserver.service.ICityService;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.City;

@RestController
@RequestMapping("/city")
@Slf4j
public class CityWebController {

    @Autowired
    private ICityService cityService;

    /*
     * 获取城市信息。
     * */
    @GetMapping("/get/{cityId}")
    public ResultData<City> get(@PathVariable Integer cityId) {
        return new ResultData<City>().setData(cityService.getById(cityId));
    }

    /**
     * 城市列表。
     */
    @GetMapping("/list")
    public ResultData<List<City>> list() {
        return new ResultData<List<City>>().setData(cityService.list());
    }

}
