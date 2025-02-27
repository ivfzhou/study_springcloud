package cn.ivfzhou.reserve_platform.searchserver.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.entity.db.City;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.event.EventListener;
import cn.ivfzhou.reserve_platform.event.constant.EventConstant;
import cn.ivfzhou.reserve_platform.feign.CityFeign;
import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

@Component
public class HotelEventListener implements EventListener<Hotel> {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private CityFeign cityFeign;

    @Override
    public String getEventType() {
        return EventConstant.EVENT_HOTEL_INSERT;
    }

    @Override
    public void eventHandler(Hotel msg) {
        System.out.println("搜索服务监听到酒店新增事件：" + msg);

        //通过酒店信息搜索对应的城市对象
        ResultData<City> cityResultData = cityFeign.queryName(msg.getCid());
        msg.setCity(cityResultData.getData());

        searchService.insertDoc(msg);
    }

}
