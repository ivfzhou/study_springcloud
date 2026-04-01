package cn.ivfzhou.springcloud.cityserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 城市服务启动类。
 * <p>
 * 提供城市信息的增删改查功能，并监听酒店事件以维护城市下的酒店数量。
 * 通过 MyBatis-Plus 实现 ORM，使用 Spring Cache 进行缓存，
 * 注册到 Eureka 服务注册中心以实现服务发现。
 * </p>
 */
@SpringBootApplication(scanBasePackages = "cn.ivfzhou.springcloud")
@MapperScan("cn.ivfzhou.springcloud.cityserver.dao")
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableCaching
public class CityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityServerApplication.class, args);
    }

}
