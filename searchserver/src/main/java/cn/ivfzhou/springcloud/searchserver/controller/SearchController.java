package cn.ivfzhou.springcloud.searchserver.controller;

import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.entity.db.SearchInfo;
import cn.ivfzhou.springcloud.searchserver.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    /**
     * 进行酒店搜索。
     */
    @RequestMapping("/list")
    public ResultData<List<Hotel>> searchHotal(SearchInfo searchInfo) {
        System.out.println("客户端接收到搜索信息：" + searchInfo);
        List<Hotel> hotels = searchService.searchByKeyWord(searchInfo);
        System.out.println("搜索的结果--------" + hotels.size());
        hotels.forEach(System.out::println);

        return new ResultData<List<Hotel>>().setData(hotels);
    }

}
