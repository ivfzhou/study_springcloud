package cn.ivfzhou.reserve_platform.cityserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.reserve_platform")
@EnableEurekaClient
@MapperScan("cn.ivfzhou.reserve_platform.cityserver.dao")
@EnableTransactionManagement
public class CityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityServerApplication.class, args);
    }

}
