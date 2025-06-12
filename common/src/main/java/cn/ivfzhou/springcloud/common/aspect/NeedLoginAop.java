package cn.ivfzhou.springcloud.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ivfzhou.springcloud.common.constant.ResultDataCode;
import cn.ivfzhou.springcloud.common.util.JwtUtil;
import cn.ivfzhou.springcloud.common.util.UserUtil;
import cn.ivfzhou.springcloud.entity.ResultData;
import cn.ivfzhou.springcloud.entity.db.User;

/**
 * 切面类
 * <p>
 * 前置
 * 后置
 * 环绕
 * 异常
 * 后置完成
 */
@Aspect
@Component
@Slf4j
public class NeedLoginAop {

    @Autowired
    private JwtUtil jwtUtil;

    @Around("@annotation(cn.ivfzhou.springcloud.common.annotation.NeedLogin)")
    public Object pointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求凭证。
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("Authorization");

        // 判断是否登录。
        User user = jwtUtil.validateToken(token);
        if (user == null) {
            // 强制登录。通知前端。
            return new ResultData<>().setCode(ResultDataCode.TO_LOGIN);
        }

        // 设置登录的用户信息。
        UserUtil.setUser(user);
        return joinPoint.proceed();
    }

}
