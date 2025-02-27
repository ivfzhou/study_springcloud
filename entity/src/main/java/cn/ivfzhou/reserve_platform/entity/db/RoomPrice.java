package cn.ivfzhou.reserve_platform.entity.db;

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

@Data
@Accessors(chain = true)
@TableName("t_room_price")
public class RoomPrice implements Serializable {

    @TableId(type = IdType.AUTO)
    @Field(type = FieldType.Integer)
    private Integer id;

    private Integer rid;

    @Field(type = FieldType.Date, format = DateFormat.date)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Transient
    private Integer type;

    private Integer number;

    private Integer hasNumber;

}
