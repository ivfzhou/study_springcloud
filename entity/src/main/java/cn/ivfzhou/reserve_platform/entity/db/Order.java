package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_order")
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String oid;

    private Integer uid;

    private Integer hid;

    private Integer rid;

    private Integer number;

    private String name;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer days;

    private double allPrice;

    private Date createTime = new Date();

    private Integer status = 0;

}
