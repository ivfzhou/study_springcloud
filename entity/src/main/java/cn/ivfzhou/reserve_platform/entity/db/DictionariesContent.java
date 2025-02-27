package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("t_dictionaries_content")
public class DictionariesContent implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String value;

    private Integer dictionaryId;

}
