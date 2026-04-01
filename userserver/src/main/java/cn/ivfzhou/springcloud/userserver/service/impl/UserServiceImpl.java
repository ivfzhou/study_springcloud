package cn.ivfzhou.springcloud.userserver.service.impl;

import cn.ivfzhou.springcloud.entity.db.User;
import cn.ivfzhou.springcloud.userserver.dao.UserMapper;
import cn.ivfzhou.springcloud.userserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类。
 * <p>
 * 继承 MyBatis-Plus 的 ServiceImpl，实现 IUserService 接口，
 * 提供用户表（t_user）的基础业务逻辑实现。
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
