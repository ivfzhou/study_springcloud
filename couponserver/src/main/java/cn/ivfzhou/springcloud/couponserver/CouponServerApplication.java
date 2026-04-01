package cn.ivfzhou.springcloud.couponserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 优惠券服务启动类。
 * <p>
 * 提供优惠券的发布、领取、查询，优惠券模板管理，以及字典数据管理等功能。
 * 注册到 Eureka 服务注册中心，同时监听 RabbitMQ 中的优惠券抢券事件。
 * </p>
 */
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
