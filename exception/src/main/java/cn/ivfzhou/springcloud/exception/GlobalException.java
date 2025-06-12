package cn.ivfzhou.springcloud.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ivfzhou.springcloud.entity.ResultData;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception e, HttpServletRequest request) {
        log.error("统一异常处理", e);
        ModelAndView modelAndView = new ModelAndView();
        String header = request.getHeader("X-Requested-With");
        // 判断是否异步请求。
        if (header != null && header.equals("XMLHttpRequest")) {
            log.info("异步请求");
            return new ResultData<>().setCode(ResultData.Code.ERROR).setMsg("服务器繁忙，请稍后再试！");
        }
        modelAndView.setViewName("myerror");
        // 同步请求。
        return modelAndView;
    }

}
