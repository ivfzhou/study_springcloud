package cn.ivfzhou.reserve_platform.common.login;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import cn.ivfzhou.reserve_platform.entity.ResultData;
import cn.ivfzhou.reserve_platform.entity.db.User;
import cn.ivfzhou.reserve_platform.common.util.JwtUtil;

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
public class LoginAop {

    /**
     * 环绕增强。
     */
    @Around("@annotation(cn.ivfzhou.reserve_platform.common.login.IsLogin)")
    public Object isLogin(ProceedingJoinPoint joinPoint) {
        // 前置增强。
        // 通过请求获得jwtToken和devId。
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String jwtToken = request.getParameter("jwtToken");
        String devId = request.getParameter("devId");

        // 判断是否登录。
        User user = null;
        if (jwtToken != null && devId != null) {
            // 有可能登录。
            user = JwtUtil.isToken(jwtToken, devId);
        }
        if (user == null) {
            // 说明未登录。
            // 获取方法上的IsLogin注解，判断mustLogin方法的返回值。
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            boolean flag = isLogin.mustLogin();
            if (flag) {
                // 强制登录。通知前端。
                return new ResultData<>().setCode(ResultData.Code.TO_LOGIN);
            }
        }

        // 设置登录的用户信息。
        UserUtil.setUser(user);
        Object result = null;
        try {
            // 目标方法的调用。
            result = joinPoint.proceed();
            // 后置增强。
        } catch (Throwable e) {
            log.error("切片调用方法失败", e);
            // 异常增强。
        } finally {
            // 后置完成增强。
        }

        return result;
    }

}
