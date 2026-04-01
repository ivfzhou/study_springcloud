package cn.ivfzhou.springcloud.systemserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 系统管理后台服务启动类。
 * <p>
 * 提供酒店管理、客房管理、优惠券管理、字典管理等后台管理功能。
 * 启用了服务发现、事务管理、缓存和定时任务。
 * </p>
 */
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class SystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServerApplication.class, args);
    }

}
