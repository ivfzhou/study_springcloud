package cn.ivfzhou.reserve_platform.cityserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.City;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.User;
import cn.ivfzhou.reserve_platform.common.login.UserUtil;
import cn.ivfzhou.reserve_platform.cityserver.service.ICityService;

@RestController
@RequestMapping("/city")
@Slf4j
public class CityController {

    @Autowired
    private ICityService cityService;

    /**
     * 添加城市接口
     */
    @PutMapping("/save")
    public ResultData<Boolean> citySave(@RequestBody City city) {
        log.info("添加城市：" + city);
        return new ResultData<Boolean>().setData(cityService.save(city));
    }

    /**
     * 城市列表
     */
    @GetMapping("/list")
    public ResultData<List<City>> cityList() {
        User user = UserUtil.getUser();
        log.info("登录的用户信息：" + user);
        return new ResultData<List<City>>().setData(cityService.list());
    }

    /**
     * 修改城市对应的酒店数量。
     */
    @PostMapping("/update/{cid}/{number}")
    public ResultData<Boolean> updateCityHotelNumber(@PathVariable Integer cid, @PathVariable Integer number) {
        int result = cityService.updateCityNumber(cid, number);
        return new ResultData<Boolean>().setData(result > 0);
    }

    @GetMapping("/queryName/{cid}")
    public ResultData<City> queryName(@PathVariable Integer cid) {
        return new ResultData<City>().setData(cityService.getById(cid));
    }

}
