package cn.ivfzhou.springcloud.userserver.service;

import cn.ivfzhou.springcloud.entity.db.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务接口。
 * <p>
 * 继承 MyBatis-Plus 的 IService，提供用户表（t_user）的基础业务操作。
 * </p>
 */
public interface IUserService extends IService<User> {

}
