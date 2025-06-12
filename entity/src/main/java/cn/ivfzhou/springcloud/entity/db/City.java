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

@Data
@Accessors(chain = true)
@TableName("t_city")
public class City implements Serializable {

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
