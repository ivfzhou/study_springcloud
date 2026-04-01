package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 酒店实体类。
 * <p>
 * 对应数据库表 t_hotel，同时作为 Elasticsearch（hotel_index）中的文档对象使用。
 * 支持中文分词（ik_max_word）和拼音搜索，支持地理位置查询（GeoPoint）。
 * 包含酒店基本信息、房间列表、平均价格、距离等搜索相关字段。
 * </p>
 */
@Data
@Accessors(chain = true)
@Document(indexName = "hotel_index")
@TableName("t_hotel")
public class Hotel implements Serializable {

    /** 酒店ID，主键自增 */
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer, index = false)
    private Integer id;

    /** 酒店名称，支持中文分词和拼音搜索 */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String hotelName;

    /** 酒店封面图片URL */
    @Field(type = FieldType.Keyword, index = false)
    private String hotelImage;

    /** 酒店类型 */
    @Field(type = FieldType.Integer)
    private Integer type;

    /** 酒店详细介绍，支持中文分词搜索 */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String hotelInfo;

    /** 搜索关键词，支持中文分词和拼音搜索 */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String keyword;

    /** 经度（不存入ES） */
    @Transient
    private double lon;

    /** 纬度（不存入ES） */
    @Transient
    private double lat;

    /** 地理位置（经度,纬度），用于ES地理距离查询 */
    @GeoPointField
    @TableField(exist = false)
    private Double[] myLocation = new Double[2];

    /** 酒店星级 */
    @Field(type = FieldType.Integer)
    private Integer star;

    /** 酒店品牌，支持中文分词和拼音搜索 */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String brand;

    /** 酒店地址，支持中文分词搜索 */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String address;

    /** 酒店开业时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(type = FieldType.Date, format = DateFormat.date)
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date openTime;

    /** 所属城市ID（不存入ES） */
    @Transient
    private Integer cid;

    /** 所属区域/行政区，支持中文分词、拼音和精确匹配搜索 */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin"),
                    @InnerField(type = FieldType.Keyword, suffix = "keyword")
            }
    )
    private String district;

    /** 创建时间（不存入ES） */
    @Transient
    private Date createTime = new Date();

    /** 状态标记（不存入ES） */
    @Transient
    private Integer status = 0;

    /** 关联的城市信息，ES嵌套对象 */
    @TableField(exist = false)
    @Field(type = FieldType.Nested)
    private City city;

    /** 酒店关联的房间列表，ES嵌套对象 */
    @TableField(exist = false)
    @Field(type = FieldType.Nested)
    private List<Room> rooms = new ArrayList<>();

    /** 酒店平均价格（ES脚本计算字段） */
    @TableField(exist = false)
    @ScriptedField
    private double avgPrice;

    /** 酒店与查询位置的距离（ES脚本计算字段，单位km） */
    @TableField(exist = false)
    @ScriptedField
    private double distance;

    // 点击率字段，用于搜索结果排序加权。
    @TableField(exist = false)
    @Field(type = FieldType.Long)
    private Integer clickRate = 0;

    /**
     * 设置经度，同时更新地理位置数组的第一个元素。
     *
     * @param lon 经度值
     * @return 当前对象（链式调用）
     */
    public Hotel setLon(double lon) {
        this.lon = lon;
        this.myLocation[0] = lon;
        return this;
    }

    /**
     * 设置纬度，同时更新地理位置数组的第二个元素。
     *
     * @param lat 纬度值
     * @return 当前对象（链式调用）
     */
    public Hotel setLat(double lat) {
        this.lat = lat;
        this.myLocation[1] = lat;
        return this;
    }

    /**
     * 设置平均价格，保留两位小数。
     *
     * @param avgPrice 平均价格
     */
    public void setAvgPrice(double avgPrice) {
        // 格式化平均价格
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        try {
            this.avgPrice = decimalFormat.parse(avgPrice + "").doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置距离，保留两位小数。
     *
     * @param distance 距离值（单位km）
     */
    public void setDistance(double distance) {
        // 格式化距离
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        try {
            this.distance = decimalFormat.parse(distance + "").doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
