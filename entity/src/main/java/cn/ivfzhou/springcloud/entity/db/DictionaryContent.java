package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据字典内容实体类。
 * <p>
 * 对应数据库表 t_dictionary_content，存储数据字典下的具体内容项。
 * 例如字典为"酒店类型"时，内容项可能包含"商务酒店"、"度假酒店"等。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_dictionary_content")
public class DictionaryContent implements Serializable {

    /** 内容ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 字典项显示名称 */
    private String name;

    /** 字典项实际值 */
    private String value;

    /** 关联的字典ID */
    private Integer dictionaryId;

}
