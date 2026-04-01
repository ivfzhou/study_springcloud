package cn.ivfzhou.springcloud.purchaseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 抢购服务启动类。
 * <p>
 * 提供优惠券抢购相关微服务功能，基于 Redis Lua 脚本保证库存扣减的原子性。
 * 启用了服务发现、事务管理、缓存和定时任务。
 * </p>
 */
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class PurchaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseServerApplication.class, args);
    }

}
