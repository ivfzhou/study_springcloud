package cn.ivfzhou.springcloud.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemException implements ErrorController {

    /**
     * 处理系统异常。
     */
    @RequestMapping("/error")
    public String systemHandler(HttpServletResponse response) {

        // 获得系统异常的响应码。
        int status = response.getStatus();

        switch (status) {
            case 404:
                return "404";
        }

        return "myerror";
    }

    /**
     * 出现系统异常（没有到达 controller 的请求的异常）。
     */
    public String getErrorPath() {
        return "/error";
    }

}
