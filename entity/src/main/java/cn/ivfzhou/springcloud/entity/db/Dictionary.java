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

/**
 * 数据字典实体类。
 * <p>
 * 对应数据库表 t_dictionary，存储系统数据字典的分类信息（如酒店类型、品牌、设施等）。
 * 每个字典下包含多个字典内容项（DictionaryContent）。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_dictionary")
public class Dictionary implements Serializable {

    /** 字典ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 字典名称 */
    private String name;

    /** 创建时间，默认为当前时间 */
    private Date createTime = new Date();

    /** 状态标记 */
    private Integer status;

    /** 字典下的内容项列表（非数据库字段，需关联查询） */
    @TableField(exist = false)
    private List<DictionaryContent> contents;

}
