package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 酒店客房类型实体类。
 * <p>
 * 非独立数据库表实体，作为 Hotel 的嵌套对象存储在 Elasticsearch 中。
 * 包含房间类型名称、面积、床型、默认价格和每日价格列表等信息。
 * </p>
 */
@Data
@Accessors(chain = true)
public class Room implements Serializable {

    /** 客房类型ID */
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer)
    private Integer id;

    /** 所属酒店ID */
    @Field(type = FieldType.Integer)
    private Integer hid;

    /** 房间类型名称，支持中文分词和拼音搜索 */
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin")
    )
    private String title;

    /** 房间面积（不存入ES） */
    @Transient
    private String area;

    /** 房间数量（不存入ES） */
    @Transient
    private Integer num;

    /** 房间图片URL（不存入ES） */
    @Transient
    private String image;

    /** 房间描述信息，支持中文分词搜索 */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String info;

    /** 床型描述（不存入ES） */
    @Transient
    private String bed;

    /** 房间可用数量 */
    @Field(type = FieldType.Integer)
    private Integer number;

    /** 默认价格（不存入ES） */
    @Transient
    private BigDecimal defaultPrice;

    /** 每日价格列表，ES嵌套对象 */
    @TableField(exist = false)
    @Field(type = FieldType.Nested)
    private List<RoomPrice> prices = new ArrayList<>();

}
