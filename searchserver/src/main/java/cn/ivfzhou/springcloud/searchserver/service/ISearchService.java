package cn.ivfzhou.springcloud.searchserver.service;

import cn.ivfzhou.springcloud.entity.db.Hotel;
import cn.ivfzhou.springcloud.entity.db.Room;
import cn.ivfzhou.springcloud.entity.db.RoomPrice;
import cn.ivfzhou.springcloud.entity.db.SearchInfo;
import co.elastic.clients.elasticsearch._types.ScriptField;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.redis.core.query.SortQueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISearchService {

    /**
     * 创建索引库
     */
    boolean createIndex();

    /**
     * 删除索引库
     */
    boolean deleteIndex();

    /**
     * 添加文档
     */
    boolean insertDoc(Hotel hotal);

    /**
     * 新增客房信息
     */
    boolean insertRoom(Room room);

    /**
     * 新增客房每日价格信息
     */
    boolean insertRoomPrice(Integer hid, List<RoomPrice> roomPrices);

    boolean updateRoomPrice(Integer hid, RoomPrice roomPrice);

    boolean updateRoomNumber(Integer hid, Integer rid, Integer number, Date beginTime, Date endTime);

    /**
     * 修改文档
     */
    boolean updateDoc(Map<String, Object> params, Integer id);

    /**
     * 删除文档
     */
    boolean deleteDocById(Integer id);

    void syncDataBase();

    List<Hotel> searchByKeyWord(SearchInfo searchInfo);

    List<Hotel> search(QueryBuilders queryBuilder, List<SortQueryBuilder<?>> sort, ScriptField... scriptFields);

    /**
     * 更新酒店的点击率
     */
    void updateHotelDjl(Integer hid, Integer djl);

}
