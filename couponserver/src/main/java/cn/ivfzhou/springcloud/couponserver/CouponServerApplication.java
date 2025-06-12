package cn.ivfzhou.springcloud.couponserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.springcloud")
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableCaching
@MapperScan("cn.ivfzhou.springcloud.couponserver.dao")
public class CouponServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponServerApplication.class, args);
    }

}
