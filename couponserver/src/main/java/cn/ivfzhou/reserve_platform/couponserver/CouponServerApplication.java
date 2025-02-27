package cn.ivfzhou.reserve_platform.couponserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.reserve_platform")
@EnableEurekaClient
@MapperScan("cn.ivfzhou.reserve_platform.couponserver.dao")
@EnableCaching
public class CouponServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponServerApplication.class, args);
    }

}
