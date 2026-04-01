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
 * 登录校验切面类。
 * <p>
 * 通过环绕通知拦截所有标注了 {@link NeedLogin} 注解的 Controller 方法，
 * 在方法执行前校验请求头中的 JWT Token 是否有效。
 * 校验通过后将用户信息存入 {@link UserUtil} 的 ThreadLocal 中，供后续业务使用。
 * 校验失败则直接返回需要登录的响应，阻止方法执行。
 * </p>
 *
 * <p>切面类型：</p>
 * <ul>
 *   <li>前置 - 在目标方法执行前</li>
 *   <li>后置 - 在目标方法正常返回后</li>
 *   <li>环绕 - 包裹整个目标方法执行过程（本类使用此类型）</li>
 *   <li>异常 - 在目标方法抛出异常后</li>
 *   <li>后置完成 - 在目标方法返回后（无论是否异常）</li>
 * </ul>
 */
@Aspect
@Component
@Slf4j
public class NeedLoginAop {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 环绕通知：拦截标注了 @NeedLogin 注解的方法，校验登录状态。
     *
     * @param joinPoint 切点信息，包含目标方法的相关信息
     * @return 方法执行结果，未登录时返回需要登录的提示
     * @throws Throwable 目标方法执行可能抛出的异常
     */
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
