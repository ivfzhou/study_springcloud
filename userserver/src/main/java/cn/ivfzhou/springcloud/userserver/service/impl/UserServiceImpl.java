package cn.ivfzhou.springcloud.userserver.service.impl;

import cn.ivfzhou.springcloud.entity.db.User;
import cn.ivfzhou.springcloud.userserver.dao.UserMapper;
import cn.ivfzhou.springcloud.userserver.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
