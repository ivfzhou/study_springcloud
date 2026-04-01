package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客房每日价格实体类。
 * <p>
 * 对应数据库表 t_room_price，记录每个客房类型在特定日期的价格和库存信息。
 * 同时作为 Room 的嵌套对象存储在 Elasticsearch 中，用于搜索时的价格过滤。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_room_price")
public class RoomPrice implements Serializable {

    /** 价格记录ID */
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer)
    private Integer id;

    /** 关联的客房类型ID */
    private Integer rid;

    /** 价格生效日期 */
    @Field(type = FieldType.Date, format = DateFormat.date)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /** 当日价格 */
    @Field(type = FieldType.Double)
    private BigDecimal price;

    /** 价格类型（不存入ES），如平日/周末/节假日 */
    @Transient
    private Integer type;

    /** 总房间数量 */
    private Integer number;

    /** 已售/已预订房间数量 */
    private Integer hasNumber;

}
