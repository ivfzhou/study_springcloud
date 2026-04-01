package cn.ivfzhou.springcloud.entity.db;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 酒店搜索条件封装类。
 * <p>
 * 非数据库实体，用于封装用户在前端发起酒店搜索时的各项查询条件。
 * 包含关键词、日期范围、价格区间、城市、地理位置和排序方式等参数。
 * </p>
 */
@Data
@Accessors(chain = true)
public class SearchInfo implements Serializable {

    /** 搜索关键词（酒店名称/品牌等） */
    private String keyword;

    /** 入住日期，格式 yyyy-MM-dd */
    private String beginTime;

    /** 离店日期，格式 yyyy-MM-dd */
    private String endTime;

    /** 最低价格 */
    private BigDecimal minPrice;

    /** 最高价格 */
    private BigDecimal maxPrice;

    /** 目标城市名称 */
    private String cityName;

    /** 用户位置纬度（用于距离排序） */
    private double lat;

    /** 用户位置经度（用于距离排序） */
    private double lon;

    /** 排序方式，1-默认，2-价格升序，3-价格降序，4-距离排序 */
    private Integer sortType = 1;

}
