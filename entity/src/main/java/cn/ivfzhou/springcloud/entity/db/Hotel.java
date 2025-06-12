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

@Data
@Accessors(chain = true)
@Document(indexName = "hotel_index")
@TableName("t_hotel")
public class Hotel implements Serializable {

    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer, index = false)
    private Integer id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String hotelName;

    @Field(type = FieldType.Keyword, index = false)
    private String hotelImage;

    @Field(type = FieldType.Integer)
    private Integer type;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String hotelInfo;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String keyword;

    @Transient
    private double lon;

    @Transient
    private double lat;

    @GeoPointField
    @TableField(exist = false)
    private Double[] myLocation = new Double[2];

    @Field(type = FieldType.Integer)
    private Integer star;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String brand;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(type = FieldType.Date, format = DateFormat.date)
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date openTime;

    @Transient
    private Integer cid;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin"),
                    @InnerField(type = FieldType.Keyword, suffix = "keyword")
            }
    )
    private String district;

    @Transient
    private Date createTime = new Date();

    @Transient
    private Integer status = 0;

    @TableField(exist = false)
    @Field(type = FieldType.Nested)
    private City city;

    @TableField(exist = false)
    @Field(type = FieldType.Nested)
    private List<Room> rooms = new ArrayList<>();

    @TableField(exist = false)
    @ScriptedField
    private double avgPrice;

    @TableField(exist = false)
    @ScriptedField
    private double distance;

    // 点击率字段。
    @TableField(exist = false)
    @Field(type = FieldType.Long)
    private Integer clickRate = 0;

    public Hotel setLon(double lon) {
        this.lon = lon;
        this.myLocation[0] = lon;
        return this;
    }

    public Hotel setLat(double lat) {
        this.lat = lat;
        this.myLocation[1] = lat;
        return this;
    }

    public void setAvgPrice(double avgPrice) {
        // 格式化平均价格
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        try {
            this.avgPrice = decimalFormat.parse(avgPrice + "").doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

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
