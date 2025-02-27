package cn.ivfzhou.reserve_platform.userserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import cn.ivfzhou.reserve_platform.userserver.dao.UserMapper;
import cn.ivfzhou.reserve_platform.userserver.service.IUserService;
import cn.ivfzhou.reserve_platform.entity.db.User;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
