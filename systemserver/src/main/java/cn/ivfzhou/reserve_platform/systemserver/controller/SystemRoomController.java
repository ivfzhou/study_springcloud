package cn.ivfzhou.reserve_platform.systemserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.Room;
import cn.ivfzhou.reserve_platform.entity.db.RoomPrice;
import cn.ivfzhou.reserve_platform.feign.HotelFeign;

@Controller
@RequestMapping("/system")
@Slf4j
public class SystemRoomController {

    @Autowired
    private HotelFeign hotelFeign;

    /**
     * 跳转到客房管理页面
     */
    @RequestMapping("/toroomadd")
    public String toroomadd() {
        return "roomadd";
    }

    /**
     * 保存客房信息
     */
    @RequestMapping("/insertroom")
    public String insertroom(Room room) {
        hotelFeign.insertRoom(room);
        return "redirect:/system/hotallist";
    }

    /**
     * 客房管理
     */
    @RequestMapping("/roomlist")
    public String roomlist(Integer hid, Model model) {
        ResultData<List<Room>> room = hotelFeign.getRoom(hid);
        model.addAttribute("rooms", room.getData());
        return "roomlist";
    }

    /**
     * 跳转到房价更新页面
     */
    @RequestMapping("/toupdateprice")
    public String toupdatePrice(Integer rid, Model model) {
        ResultData<List<RoomPrice>> roomPrice = hotelFeign.getRoomPrice(rid);
        model.addAttribute("roomprices", roomPrice.getData());
        return "roompricelist";
    }

    /**
     * 修改房间价格
     */
    @RequestMapping("/updatepirce")
    public String updatePrice(RoomPrice roomPrice) {
        hotelFeign.updatePrice(roomPrice);
        return "redirect:/system/toupdateprice?rid=" + roomPrice.getRid();
    }

}
