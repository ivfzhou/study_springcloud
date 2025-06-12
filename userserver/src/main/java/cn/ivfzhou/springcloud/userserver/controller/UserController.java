package cn.ivfzhou.springcloud.userserver.controller;

import cn.ivfzhou.springcloud.common.util.JwtUtil;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.User;
import cn.ivfzhou.springcloud.userserver.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册。
     */
    @PutMapping("/register")
    public ResultData<Integer> register(@RequestBody User user) {
        try {
            boolean save = userService.save(user);
            return new ResultData<Integer>().setData(1);
        } catch (Exception e) {
            log.error("注册失败", e);
        }
        return new ResultData<Integer>().setCode(ResultData.Code.ERROR).setMsg("用户名已经存在！");
    }

    /**
     * 登录。
     */
    @PostMapping("/login")
    public ResultData<String> login(@RequestParam String username, @RequestParam String password, @RequestParam String devId) {

        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", password);
        User user = userService.getOne(wrapper);
        if (user != null) {
            // 登录成功。
            String jwtToken = JwtUtil.createJwtToken(user, devId);
            return new ResultData<String>().setData(jwtToken);
        }

        // 登录失败。
        return new ResultData<String>().setCode(ResultData.Code.ERROR).setMsg("用户名或密码错误！");
    }

}
