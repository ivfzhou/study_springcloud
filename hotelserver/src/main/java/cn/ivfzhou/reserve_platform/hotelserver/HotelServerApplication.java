package cn.ivfzhou.reserve_platform.hotelserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.reserve_platform")
@EnableEurekaClient
@MapperScan("cn.ivfzhou.reserve_platform.hotelserver.dao")
public class HotelServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelServerApplication.class, args);
    }

}
