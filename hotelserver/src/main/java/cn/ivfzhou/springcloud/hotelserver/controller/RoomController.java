package cn.ivfzhou.springcloud.hotelserver.controller;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomPriceService;
import cn.ivfzhou.springcloud.hotelserver.service.IRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IRoomPriceService roomPriceService;

    @RequestMapping("/insert")
    public ResultData<Boolean> insert(@RequestBody Room room) {
        return new ResultData<Boolean>().setData(roomService.save(room));
    }

    @RequestMapping("/list/{hid}")
    public ResultData<List<Room>> list(@PathVariable Integer hid) {
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("hid", hid);
        List<Room> list = roomService.list(queryWrapper);
        return new ResultData<List<Room>>().setData(list);
    }

    /**
     * 获得房间价格。
     */
    @RequestMapping("/getPirce")
    public ResultData<List<RoomPrice>> getRoomPrice(@RequestParam Integer rid) {
        QueryWrapper<RoomPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rid", rid);
        List<RoomPrice> list = roomPriceService.list(queryWrapper);
        return new ResultData<List<RoomPrice>>().setData(list);
    }

    /**
     * 修改客房价格。
     */
    @RequestMapping("/updatePrice")
    public ResultData<Boolean> updatePrice(@RequestBody RoomPrice roomPrice) {
        boolean flag = roomPriceService.updateById(roomPrice);
        return new ResultData<Boolean>().setData(flag);
    }

    /**
     * 根据客房id查询对应的酒店 id。
     */
    @RequestMapping("/getHotalId/{rid}")
    public ResultData<Integer> getHotalIdByRoomId(@PathVariable Integer rid) {
        Room room = roomService.getById(rid);
        return new ResultData<Integer>().setData(room.getHid());
    }

    /**
     * 根据客房id获得客房信息。
     */
    @RequestMapping("/getRoom/{rid}")
    public ResultData<Room> getRoomById(@PathVariable Integer rid) {
        Room room = roomService.getById(rid);
        return new ResultData<Room>().setData(room);
    }

}
