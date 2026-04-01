package cn.ivfzhou.springcloud.hotelserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 酒店服务启动类。
 * <p>
 * 提供酒店管理、客房管理、客房价格管理等微服务功能。
 * 启用了服务发现、MyBatis Mapper扫描、事务管理、缓存和定时任务。
 * </p>
 */
@SpringBootApplication(scanBasePackages = "cn.ivfzhou.springcloud")
@EnableDiscoveryClient
@MapperScan("cn.ivfzhou.springcloud.hotelserver.dao")
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class HotelServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelServerApplication.class, args);
    }

}
