package cn.ivfzhou.springcloud.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API 网关服务启动类。
 * <p>
 * 作为所有微服务的统一入口，提供路由转发、请求过滤等功能。
 * 启用了服务发现和缓存功能。
 * </p>
 */
@EnableDiscoveryClient
@EnableCaching
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
