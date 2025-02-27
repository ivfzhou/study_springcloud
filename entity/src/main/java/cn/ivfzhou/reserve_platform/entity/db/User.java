package cn.ivfzhou.reserve_platform.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private Date createTime = new Date();

    private Integer status = 0;

}
