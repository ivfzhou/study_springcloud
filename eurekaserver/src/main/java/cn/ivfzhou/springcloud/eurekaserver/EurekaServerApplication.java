package cn.ivfzhou.springcloud.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka 注册中心服务启动类。
 * <p>
 * 通过 @EnableEurekaServer 注解启用 Eureka Server 功能，
 * 为所有微服务提供服务注册和发现的能力。
 * </p>
 */
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
