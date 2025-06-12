package cn.ivfzhou.springcloud.entity.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_dictionary_content")
public class DictionaryContent implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String value;

    private Integer dictionaryId;

}
