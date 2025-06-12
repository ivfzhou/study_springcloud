package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_dictionary")
public class Dictionary implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Date createTime = new Date();

    private Integer status;

    @TableField(exist = false)
    private List<DictionaryContent> contents;

}
