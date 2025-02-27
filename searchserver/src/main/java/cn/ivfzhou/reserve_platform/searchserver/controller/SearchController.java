package cn.ivfzhou.reserve_platform.searchserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.SearchInfo;
import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

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
