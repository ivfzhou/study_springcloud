package cn.ivfzhou.reserve_platform.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.Room;
import cn.ivfzhou.reserve_platform.entity.db.RoomPrice;

@FeignClient("micro-hotel")
public interface HotelFeign {

    @RequestMapping("/hotel/insert")
    ResultData<Boolean> insert(@RequestBody Hotel hotel);

    @RequestMapping("/hotel/list")
    ResultData<List<Hotel>> list();

    @RequestMapping("/room/getHotelId/{rid}")
    ResultData<Integer> getHotelIdByRoomId(@PathVariable("rid") Integer rid);

    @RequestMapping("/room/insert")
    ResultData<Boolean> insertRoom(@RequestBody Room room);

    @RequestMapping("/room/list/{hid}")
    ResultData<List<Room>> getRoom(@PathVariable("hid") Integer hid);

    @RequestMapping("/room/getPirce")
    ResultData<List<RoomPrice>> getRoomPrice(@RequestParam("rid") Integer rid);

    @RequestMapping("/room/updateprice")
    ResultData<Boolean> updatePrice(@RequestBody RoomPrice roomPrice);

    @RequestMapping("/room/getRoom/{rid}")
    ResultData<Room> getRoomById(@PathVariable("rid") Integer rid);

}
