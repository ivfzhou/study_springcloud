package cn.ivfzhou.springcloud.orderserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 订单服务启动类。
 * <p>
 * 提供订单创建、订单价格计算、支付处理等微服务功能。
 * 启用了服务发现、MyBatis Mapper扫描、事务管理和缓存。
 * </p>
 */
@SpringBootApplication(scanBasePackages = "cn.ivfzhou.springcloud")
@EnableDiscoveryClient
@EnableCaching
@MapperScan("cn.ivfzhou.springcloud.orderserver.dao")
@EnableTransactionManagement
@EnableScheduling
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}
