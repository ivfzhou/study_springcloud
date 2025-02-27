package cn.ivfzhou.reserve_platform.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import cn.ivfzhou.reserve_platform.exception.GlobalException;
import cn.ivfzhou.reserve_platform.exception.SystemException;

@SpringBootApplication
@ComponentScan(basePackages = "cn.ivfzhou.reserve_platform",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {GlobalException.class, SystemException.class}))
@EnableEurekaClient
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
