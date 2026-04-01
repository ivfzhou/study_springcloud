package cn.ivfzhou.springcloud.userserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 用户服务启动类。
 * <p>
 * 提供用户的注册、登录功能，登录成功后签发 JWT Token。
 * 注册到 Eureka 服务注册中心，并启用定时任务调度。
 * </p>
 */
@SpringBootApplication(scanBasePackages = "cn.ivfzhou.springcloud")
@EnableDiscoveryClient
@MapperScan("cn.ivfzhou.springcloud.userserver.dao")
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

}
