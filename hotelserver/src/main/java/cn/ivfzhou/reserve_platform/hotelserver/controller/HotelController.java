package cn.ivfzhou.reserve_platform.hotelserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.common.bloom.BloomUtil;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.event.util.EventUtil;
import cn.ivfzhou.reserve_platform.hotelserver.service.IHotelService;

import java.util.List;


/**
 * 新增酒店
 * 新增（酒店）客房类型 - 新增价格表（30天）
 * 调整每个客房的每天价格
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private EventUtil eventUtil;

    @Autowired
    private BloomUtil bloomUtil;

    /**
     * 新增酒店
     */
    @RequestMapping("/insert")
    public ResultData<Boolean> insert(@RequestBody Hotel hotel) {
        boolean flag = hotelService.save(hotel);
        return new ResultData<Boolean>().setData(flag);
    }

    /**
     * 查询酒店列表
     */
    @RequestMapping("/list")
    public ResultData<List<Hotel>> list() {
        List<Hotel> list = hotelService.list();
        return new ResultData<List<Hotel>>().setData(list);
    }

    /**
     * 根据id查询酒店详情信息
     */
    @RequestMapping("/getInfo")
    public ResultData<Hotel> getInfo(Integer hid, String devId) {

        //查询酒店信息
        Hotel hotal = hotelService.getById(hid);

        //TODO 发生了一次点击率 ????????????????
        //boolmfilter
        if (!bloomUtil.isExists("djl", devId + "-" + hid)) {
            System.out.println(devId + " - " + hid + "算一次点击率！！！");
            eventUtil.publishEvent(EventConstant.EVENT_HOTEL_CLICK, hotal);
            //不存在，添加一次
            bloomUtil.addBloom("djl", devId + "-" + hid);
        } else {
            System.out.println(devId + " - " + hid + "已经点击过了！！！");
        }

        return new ResultData<Hotel>().setData(hotal);
    }

}
