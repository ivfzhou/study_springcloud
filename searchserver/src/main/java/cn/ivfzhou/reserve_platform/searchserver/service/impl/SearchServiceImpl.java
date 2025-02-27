package cn.ivfzhou.reserve_platform.searchserver.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.sort.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.ivfzhou.reserve_platform.entity.db.Hotel;
import cn.ivfzhou.reserve_platform.entity.db.Room;
import cn.ivfzhou.reserve_platform.entity.db.RoomPrice;
import cn.ivfzhou.reserve_platform.entity.db.SearchInfo;
import cn.ivfzhou.reserve_platform.feign.HotelFeign;
import cn.ivfzhou.reserve_platform.searchserver.service.ISearchService;

@Service
public class SearchServiceImpl implements ISearchService {

    //计算平均价格的脚本
    String script = "double avgPrice = Integer.MAX_VALUE;\n" +
            "          SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd\");\n" +
            "          \n" +
            "          if(params._source.rooms == null || params._source.rooms.length == 0){\n" +
            "            return -1;\n" +
            "          }\n" +
            "          \n" +
            "          //酒店的房型集合，依次循环所有房型\n" +
            "          for(r in params._source.rooms) {\n" +
            "              //当前房型的总价格\n" +
            "              double roomSumPrice = 0;\n" +
            "              int days = 0;\n" +
            "              //找到房型中的所有天数的价格集合\n" +
            "              for(rp in r.prices) {\n" +
            "                  \n" +
            "                  if(sdf.parse(rp.date).getTime() >= sdf.parse(params.beginTime).getTime() && sdf.parse(rp.date).getTime() < sdf.parse(params.endTime).getTime()) {\n" +
            "                    \n" +
            "                      //判断这个酒店在指定范围内是否有一天已经满了\n" +
            "                      if(rp.number == rp.hasNumber){\n" +
            "                        roomSumPrice = -1;\n" +
            "                        break;\n" +
            "                      }\n" +
            "                    \n" +
            "                      roomSumPrice += rp.price;\n" +
            "                      days++;\n" +
            "                  }\n" +
            "              }\n" +
            "  \n" +
            "              //结算当前房型的平均价\n" +
            "              if(roomSumPrice != -1){\n" +
            "                double roomAvgPrice = roomSumPrice / days;\n" +
            "                if (roomAvgPrice >= params.minPrice && roomAvgPrice <= params.maxPrice){\n" +
            "                    avgPrice = Math.min(avgPrice, roomAvgPrice);\n" +
            "                }\n" +
            "              }\n" +
            "          }\n" +
            "  \n" +
            "          return avgPrice == Integer.MAX_VALUE ? -1 : avgPrice;";
    //计算距离的脚本
    String script2 = "return doc.mylocation.planeDistance(params.lat, params.lon) / 1000;";

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private HotelFeign hotelFeign;

    @Override
    public boolean createIndex() {
        IndexOperations indexOperations = restTemplate.indexOps(Hotel.class);
        if (!indexOperations.exists()) {
            System.out.println("索引库不存在，就进行创建！");
            indexOperations.create();

            //创建索引库的映射类型
            Document document = indexOperations.createMapping();
            indexOperations.putMapping(document);

            //同步数据库到索引库
            syncDataBase();
        }

        return false;
    }

    @Override
    public boolean deleteIndex() {
        IndexOperations indexOperations = restTemplate.indexOps(Hotel.class);
        return indexOperations.delete();
    }

    @Override
    public boolean insertDoc(Hotel hotel) {
        Hotel save = restTemplate.save(hotel);
        return save != null;
    }

    @Override
    public boolean insertRoom(Room room) {

        //room -> Map对象
        Map<String, Object> roomMap = new HashMap<>();
        roomMap.put("id", room.getId());
        roomMap.put("hid", room.getHid());
        roomMap.put("info", room.getInfo());
        roomMap.put("title", room.getTitle());
        roomMap.put("number", room.getNumber());
        roomMap.put("prices", new ArrayList<>());

        Map<String, Object> params = new HashMap<>();
        params.put("roomInfo", roomMap);

        UpdateQuery updateQuery = UpdateQuery.builder(room.getHid() + "")
                .withScript("ctx._source.rooms.add(params.roomInfo)")
                .withParams(params)
                .build();

        UpdateResponse response = restTemplate.update(updateQuery, IndexCoordinates.of("hotel_index"));
        return response.getResult() == UpdateResponse.Result.UPDATED;
    }

    /**
     * 新增酒店对应的房价价格
     */
    @Override
    public boolean insertRoomPrice(Integer hid, List<RoomPrice> roomPrices) {

        List<UpdateQuery> updateQueries = new ArrayList<>();

        //循环价格集合
        roomPrices.forEach(roomPrice -> {

            //将RoomPrice -> Map集合
            Map<String, Object> priceMap = new HashMap<>();
            priceMap.put("id", roomPrice.getId());
            priceMap.put("rid", roomPrice.getRid());
            priceMap.put("price", roomPrice.getPrice().doubleValue());
            priceMap.put("number", roomPrice.getNumber());
            priceMap.put("hasNumber", roomPrice.getHasNumber());
            priceMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(roomPrice.getDate()));

            //脚本的参数集合
            Map<String, Object> params = new HashMap<>();
            params.put("rid", roomPrice.getRid());
            params.put("priceInfo", priceMap);

            UpdateQuery updateQuery = UpdateQuery.builder(hid + "")
                    .withScript("for(r in ctx._source.rooms){ if(r.id == params.rid){ r.prices.add(params.priceInfo); } }")
                    .withParams(params)
                    .build();

            updateQueries.add(updateQuery);

        });

        //批量修改
        restTemplate.bulkUpdate(updateQueries, IndexCoordinates.of("hotel_index"));
        return true;
    }

    /**
     * 调整酒店对应的房价信息
     */
    @Override
    public boolean updateRoomPrice(Integer hid, RoomPrice roomPrice) {

        Map<String, Object> map = new HashMap<>();
        map.put("rid", roomPrice.getRid());
        map.put("rpid", roomPrice.getId());
        map.put("price", roomPrice.getPrice());

        String script = "for(r in ctx._source.rooms){ if(r.id == params.rid) { for(rp in r.prices) { if(rp.id == params.rpid) { rp.price = params.price; } } } }";

        UpdateQuery updateQuery = UpdateQuery.builder(hid + "")
                .withScript(script)
                .withParams(map)
                .build();

        UpdateResponse response = restTemplate.update(updateQuery, IndexCoordinates.of("hotel_index"));
        return true;
    }

    /**
     * 修改对应房型的房间数量 - 指定的时间范围内（被下单）
     */
    @Override
    public boolean updateRoomNumber(Integer hid, Integer rid, Integer number, Date beginTime, Date endTime) {

        String script = "SimpleDateFormat dfs = new SimpleDateFormat('yyyy-MM-dd');" +
                "for(r in ctx._source.rooms){" +
                "if(r.id == params.rid){" +
                " for(rp in r.prices) {" +
                " if(dfs.parse(rp.date).getTime() >= dfs.parse(params.beginTime).getTime() && dfs.parse(rp.date).getTime() < dfs.parse(params.endTime).getTime()){" +
                " rp.number += params.number " +
                "}}}}";

        Map<String, Object> params = new HashMap<>();
        params.put("rid", rid);
        params.put("number", number);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);

        UpdateQuery updateQuery = UpdateQuery.builder(hid + "")
                .withScript(script)
                .withParams(params)
                .build();

        UpdateResponse response = restTemplate.update(updateQuery, IndexCoordinates.of("hotel_index"));
        return true;
    }

    @Override
    public boolean updateDoc(Map<String, Object> params, Integer id) {

        Document document = Document.create();

        params.forEach(document::append);

        UpdateQuery updateQuery = UpdateQuery.builder(id + "")
                .withDocument(document)
                .build();

        UpdateResponse response = restTemplate.update(updateQuery, IndexCoordinates.of("hotel_index"));
        return response.getResult() == UpdateResponse.Result.UPDATED;
    }

    @Override
    public boolean deleteDocById(Integer id) {
        String delete = restTemplate.delete(id + "", Hotel.class);
        System.out.println("删除结果：" + delete);
        return delete != null;
    }

    /**
     * 将数据库现有的数据同步到ES中
     */
    @Override
    public void syncDataBase() {
        //查询数据库
        List<Hotel> data = hotelFeign.list().getData();

        //依次添加到ES
        data.forEach(hotel -> {
            System.out.println("同步的酒店对象：" + hotel);
            this.insertDoc(hotel);
        });
    }

    /**
     * 根据关键词搜索
     * <p>
     * 关键词怎么搜索？
     * - hotelName、keyword、brand、district、rooms.title
     * - hotelInfo、address
     * <p>
     * 时间范围： - 时间范围内酒店是否有空余方法
     * - 入住时间
     * - 离店时间
     * <p>
     * 价格区间
     * - 最小价格 200
     * - 最大价格 400
     * <p>
     * 位置 - 经纬度 - 距离信息
     */
    @Override
    public List<Hotel> searchByKeyWord(SearchInfo searchInfo) {

        //酒店复合查询
        BoolQueryBuilder hotelQuery = QueryBuilders.boolQuery();

        //匹配时间范围
        if (searchInfo.getBeginTime() != null && searchInfo.getEndTime() != null) {

            //房间价格时间范围
            RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery("date")
                    .gte(searchInfo.getBeginTime())
                    .lt(searchInfo.getEndTime());

            //是否已经预订满了 - 脚本查询
            ScriptQueryBuilder scriptQuery = QueryBuilders.scriptQuery(new Script("doc['rooms.prices.number'].value == doc['rooms.prices.hasNumber'].value"));

            //查询房间价格 - 有一天已经被预订满的房间
            BoolQueryBuilder roomPriceQuery = QueryBuilders.boolQuery()
                    .must(timeRangeQuery)
                    .must(scriptQuery);

            //房间价格的嵌套查询
            NestedQueryBuilder pricesNestedQuery = QueryBuilders.nestedQuery("rooms.prices", roomPriceQuery, ScoreMode.Avg);

            //rooms的布尔查询
            BoolQueryBuilder roomsBoolQuery = QueryBuilders.boolQuery()
                    .mustNot(pricesNestedQuery);

            //酒店房间的嵌套查询
            NestedQueryBuilder roomsNestedQuery = QueryBuilders.nestedQuery("rooms", roomsBoolQuery, ScoreMode.Avg);
            //时间范围限制的查询
            hotelQuery.must(roomsNestedQuery);
        }

        //关键词查询匹配
        if (!StringUtils.isEmpty(searchInfo.getKeyword())) {
            MultiMatchQueryBuilder keywordQuery = QueryBuilders.multiMatchQuery(searchInfo.getKeyword())
                    .field("hotelName", 5)
                    .field("hotelName.pinyin")
                    .field("hotelInfo")
                    .field("brand", 4)
                    .field("brand.pinyin")
                    .field("keyword", 3)
                    .field("keyword.pinyin")
                    .field("district", 3)
                    .field("district.pinyin");

            //设置muster
            hotelQuery.must(keywordQuery);
        }

        //根据城市查询
        NestedQueryBuilder cityQuery = QueryBuilders.nestedQuery("city", QueryBuilders.matchQuery("city.cityName.keyword", searchInfo.getCityName()), ScoreMode.Avg);
        //总查询
        BoostingQueryBuilder execQuery = QueryBuilders.boostingQuery(hotelQuery, cityQuery).negativeBoost(10);

        //评分查询 - 执行该评分查询
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(execQuery,
                        ScoreFunctionBuilders.fieldValueFactorFunction("djl").setWeight(1f))
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .boostMode(CombineFunction.MULTIPLY);


        //---------------------------------------------------------------------------------------------------------------

        //自定义脚本的参数
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", searchInfo.getBeginTime());
        params.put("endTime", searchInfo.getEndTime());
        params.put("minPrice", searchInfo.getMinPrice());
        params.put("maxPrice", searchInfo.getMaxPrice());
        params.put("lat", searchInfo.getLat());
        params.put("lon", searchInfo.getLon());

        //设置自定义字段
        ScriptField avgPrice = new ScriptField("avgPrice",
                new Script(ScriptType.INLINE, "painless", script, params));

        //设置距离的自定义字段
        ScriptField distance = new ScriptField("distance",
                new Script(ScriptType.INLINE, "painless", script2, params));

        //处理排序
        List<SortBuilder<?>> sortBuilders = new ArrayList<>();
        switch (searchInfo.getSortType()) {
            case 1:
                //智能排序
                break;
            case 2:
                //价格排序
                ScriptSortBuilder priceSortBuilder = SortBuilders.scriptSort(new Script(ScriptType.INLINE, "painless", script, params), ScriptSortBuilder.ScriptSortType.NUMBER)
                        .order(SortOrder.ASC);
                sortBuilders.add(priceSortBuilder);
                break;
            case 3:
                //距离排序
                GeoDistanceSortBuilder geoSortBuilder
                        = SortBuilders.geoDistanceSort("mylocation", searchInfo.getLat(), searchInfo.getLon()).order(SortOrder.ASC);
                sortBuilders.add(geoSortBuilder);
                break;
        }

        //执行查询
        return search(functionScoreQuery, sortBuilders, avgPrice, distance);
    }

    /**
     * 搜索
     */
    @Override
    public List<Hotel> search(QueryBuilder queryBuilder, List<SortBuilder<?>> sort, ScriptField... scriptFields) {


        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder, null, sort);


        //处理脚本的自定义字段
        if (scriptFields != null) {
            //设置自定义字段
            nativeSearchQuery.addScriptField(scriptFields);
            //设置显示的字段
            String[] excluded = {"rooms", "openTime"};
            nativeSearchQuery.addSourceFilter(new FetchSourceFilter(null, excluded));
        }

        //执行查询
        SearchHits<Hotel> hits = restTemplate.search(nativeSearchQuery, Hotel.class);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        List<Hotel> hotels = new ArrayList<>();
        hits.getSearchHits().forEach(hotelSearchHit -> {
            //hotelSearchHit - 查询结果
            Hotel hotel = hotelSearchHit.getContent();
            if (hotel.getAvgPrice() > 0) {

                //打印酒店的评分
                float score = hotelSearchHit.getScore();
                System.out.println(hotel.getHotelName() + " 评分：" + score);

                //手动格式化价格和距离
                hotel.setAvgPrice(Double.parseDouble(decimalFormat.format(hotel.getAvgPrice())));
                hotel.setDistance(Double.parseDouble(decimalFormat.format(hotel.getDistance())));

                hotels.add(hotel);
            }
        });

        return hotels;
    }

    /**
     * 更新点击率
     */
    @Override
    public void updateHotelDjl(Integer hid, Integer djl) {
        Map<String, Object> params = new HashMap<>();
        params.put("djl", djl);

        UpdateQuery updateQuery = UpdateQuery.builder(hid + "")
                .withScript("ctx._source.djl += params.djl")
                .withParams(params)
                .build();

        restTemplate.update(updateQuery, IndexCoordinates.of("hotel_index"));
    }

}
