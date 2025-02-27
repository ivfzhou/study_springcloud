package cn.ivfzhou.reserve_platform.micro_service_search;

import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchServerApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Test
    void contextLoads() {
        //创建索引库、映射类型
//        searchService.createIndex();

        //添加文档
//        Hotal hotal = new Hotal()
//                .setId(2)
//                .setHotalImage("http://www.baidu.com")
//                .setAddress("123123qwe广东省深圳市福田路28号12312312123")
//                .setBrand("8qweqweqwe天123123")
//                .setDistrict("福田区123123")
//                .setHotalInfo("快捷酒店123123")
//                .setHotalName("福田区7天优品连锁快捷品牌酒店123")
//                .setKeyword("快捷,便宜,方便,临近地铁,考场周边123")
//                .setOpenTime(new Date())
//                .setLon(122.23123131)
//                .setLat(45.1241233)
//                .setStar(0)
//                .setType(1);
//
//        searchService.insertDoc(hotal);

        //修改酒店
//        Map<String, Object> map = new HashMap<>();
//        map.put("hotalName", "修改后的酒店");
//
//        searchService.updateDoc(map, 1);

        //删除酒店
//        searchService.deleteDocById(1);

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //同步索引库
//        searchService.syncDataBase();


//        //term查询
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("hotalName", "希尔顿");
//        //terms查询
//        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("hotalName", "北京", "希尔顿", "深圳");
//        //Match查询
//        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("hotalName", "北京酒店").operator(Operator.AND);
//        //mutil_math查询
//        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery("北京酒店").field("hotalName", 2.0f).field("hotalInfo", 1.5f);
//        //范围查询
//        RangeQueryBuilder rageQuery = QueryBuilders.rangeQuery("star").gte(0).lte(3);
//        //纠错查询
//        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("hotalInfo", "性价笔").fuzziness(Fuzziness.ONE).prefixLength(2);
//
//        //符合bool查询
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//                .must(termQueryBuilder)
//                .must(matchQuery)
//                .should(multiMatchQuery)
//                .should(fuzzyQueryBuilder)
//                .mustNot(rageQuery)
//                .minimumShouldMatch(1);
//
//        //boosting查询
//        QueryBuilders.boostingQuery(null, null).negativeBoost(0.5f);

//        GeoDistanceQueryBuilder geoQuery = QueryBuilders.geoDistanceQuery("mylocation")
//                .point(22.634478, 113.841844)
//                .distance(100, DistanceUnit.KILOMETERS)
//                .geoDistance(GeoDistance.PLANE);

//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//
//        List<Hotal> search = searchService.search(matchAllQueryBuilder);
//        search.stream().forEach(System.out::println);
    }

}
