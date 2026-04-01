package cn.ivfzhou.springcloud.feign;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 酒店服务 Feign 远程调用接口。
 * <p>
 * 用于其他微服务通过 OpenFeign 调用酒店服务（micro-hotel）的内部接口，
 * 实现酒店、客房、房价的增删改查操作。
 * </p>
 */
@FeignClient("micro-hotel")
public interface HotelFeign {

    /** 新增酒店信息 */
    @RequestMapping("/hotel/insert")
    ResultData<Boolean> insert(@RequestBody Hotel hotel);

    /** 查询所有酒店列表 */
    @RequestMapping("/hotel/list")
    ResultData<List<Hotel>> list();

    /** 根据客房ID查询所属酒店ID */
    @RequestMapping("/room/getHotelId/{rid}")
    ResultData<Integer> getHotelIdByRoomId(@PathVariable("rid") Integer rid);

    /** 新增客房信息 */
    @RequestMapping("/room/insert")
    ResultData<Boolean> insertRoom(@RequestBody Room room);

    /** 根据酒店ID查询该酒店的客房列表 */
    @RequestMapping("/room/list/{hid}")
    ResultData<List<Room>> getRoom(@PathVariable("hid") Integer hid);

    /** 根据客房ID查询客房价格列表 */
    @RequestMapping("/room/getPirce")
    ResultData<List<RoomPrice>> getRoomPrice(@RequestParam("rid") Integer rid);

    /** 更新客房价格 */
    @RequestMapping("/room/updateprice")
    ResultData<Boolean> updatePrice(@RequestBody RoomPrice roomPrice);

    /** 根据客房ID查询客房详情 */
    @RequestMapping("/room/getRoom/{rid}")
    ResultData<Room> getRoomById(@PathVariable("rid") Integer rid);

}
