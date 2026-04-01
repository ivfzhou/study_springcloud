package cn.ivfzhou.springcloud.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心服务启动类。
 * <p>
 * 通过 @EnableConfigServer 注解启用配置服务器功能，
 * 集中管理所有微服务的外部化配置，支持多环境配置。
 * </p>
 */
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
