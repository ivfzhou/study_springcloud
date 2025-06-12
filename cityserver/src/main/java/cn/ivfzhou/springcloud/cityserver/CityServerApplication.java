package cn.ivfzhou.springcloud.cityserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
