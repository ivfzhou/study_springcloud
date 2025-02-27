package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName("t_dictionary")
public class Dictionary implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Date createTime = new Date();

    private Integer status = 0;

    @TableField(exist = false)
    private List<DictionariesContent> contents;

}
