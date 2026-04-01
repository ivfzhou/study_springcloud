package cn.ivfzhou.springcloud.userserver.dao;

import cn.ivfzhou.springcloud.entity.db.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户数据访问接口。
 * <p>
 * 继承 MyBatis-Plus 的 BaseMapper，提供用户表（t_user）的基础 CRUD 操作。
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {
}
