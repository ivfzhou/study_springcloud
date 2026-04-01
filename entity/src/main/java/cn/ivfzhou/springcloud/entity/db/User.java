package cn.ivfzhou.springcloud.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类。
 * <p>
 * 对应数据库表 t_user，存储系统用户的基本信息，包括登录账号、密码、昵称和邮箱等。
 * </p>
 */
@Data
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    /** 用户ID，主键自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 用户名（登录账号，唯一） */
    private String username;

    /** 密码 */
    private String password;

    /** 用户昵称 */
    private String nickname;

    /** 用户邮箱 */
    private String email;

    /** 注册时间，默认为当前时间 */
    private Date createTime = new Date();

    /** 状态标记，0-正常，1-禁用 */
    private Integer status = 0;

}
