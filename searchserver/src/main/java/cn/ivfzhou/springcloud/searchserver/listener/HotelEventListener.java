package cn.ivfzhou.springcloud.searchserver.listener;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.City;
import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.feign.CityFeign;
import cn.ivfzhou.springcloud.rabbitmq.EventListener;
import cn.ivfzhou.springcloud.rabbitmq.constant.EventConstant;
import cn.ivfzhou.springcloud.searchserver.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
