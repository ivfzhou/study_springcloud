package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

/**
 * 城市实体类。
 * <p>
 * 对应数据库表 t_city，同时作为 Elasticsearch 中的文档对象使用。
 * 存储城市的基本信息，并支持中文分词（ik_max_word）和拼音搜索。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_city")
public class City implements Serializable {

    /** 城市ID，主键自增 */
    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer)
    private Integer id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word"),
            otherFields = {
                    @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin"),
                    @InnerField(type = FieldType.Keyword, suffix = "keyword")
            }
    )
    private String name;

    @Transient
    private String pinyin;

    @Transient
    private Integer hotelNumber;

    @Transient
    private LocalDateTime createTime;

    @Transient
    private Integer status;

}
