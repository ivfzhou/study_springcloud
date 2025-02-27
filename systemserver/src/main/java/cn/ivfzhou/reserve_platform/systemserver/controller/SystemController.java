package cn.ivfzhou.reserve_platform.systemserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

import cn.ivfzhou.reserve_platform.entity.db.City;
import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.feign.CityFeign;
import cn.ivfzhou.reserve_platform.feign.HotelFeign;

@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController {

    @Autowired
    private CityFeign cityFeign;

    @Autowired
    private HotelFeign hotelFeign;

    // 图片上传的路径。
    @Value("upload")
    private String uploadPath;

    /**
     * 跳转到城市添加页面。
     */
    @RequestMapping("/toCityAdd")
    public String toCityAdd() {
        return "cityadd";
    }

    /**
     * 跳转到酒店添加页面。
     */
    @RequestMapping("/toHotelAdd")
    public String toHotelAdd(Model model) {
        // 通过feign调用城市服务，获得城市列表。
        ResultData<List<City>> listResultData = cityFeign.cityList();
        model.addAttribute("citys", listResultData.getData());
        return "hoteladd";
    }

    /**
     * 添加城市。
     */
    @RequestMapping("/cityadd")
    public String cityAdd(City city) {
        // 调用城市服务，添加城市数据。
        ResultData<Boolean> flag = cityFeign.citySave(city);
        log.info("调用服务返回结果：" + flag);
        if (flag.getCode() == ResultData.Code.OK) {
            return "succ";
        }
        return "cityerror";
    }

    /**
     * 城市列表。
     */
    @RequestMapping("/citylist")
    public String cityList(Model model) {
        ResultData<List<City>> resultData = cityFeign.cityList();
        if (resultData.getCode() == ResultData.Code.OK) {
            model.addAttribute("citys", resultData.getData());
        }
        return "citylist";
    }

    /**
     * 添加酒店。
     */
    @RequestMapping("/hoteladd")
    public String hotelAdd(Hotel hotel, MultipartFile image) {
        // 处理上传文件名
        String fileName = UUID.randomUUID().toString();
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File updateFile = new File(file, fileName);

        // 处理文件上传
        try (
                InputStream in = image.getInputStream();
                OutputStream out = new FileOutputStream(updateFile);
        ) {
            // 上传
            IOUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置图片的上传路径
        hotel.setHotelImage(updateFile.getAbsolutePath().replace("\\", "/"));

        // 保存酒店信息
        ResultData<Boolean> result = hotelFeign.insert(hotel);
        if (result.getCode() == ResultData.Code.OK) {
            return "redirect:/system/hotellist";
        }

        return "systemerror";
    }

    /**
     * 酒店列表
     */
    @RequestMapping("/hotellist")
    public String hotelList(Model model) {
        ResultData<List<Hotel>> hotalList = hotelFeign.list();
        model.addAttribute("hotels", hotalList.getData());
        return "hotellist";
    }

    /**
     * 根据图片路径，获取本地的图片
     */
    @RequestMapping("/getImage")
    public void getImage(String path, HttpServletResponse response) {
        try (
                InputStream in = new FileInputStream(path);
                OutputStream out = response.getOutputStream();
        ) {
            IOUtils.copy(in, out);
        } catch (Exception e) {
            log.error("图片下载失败", e);
        }
    }

}
