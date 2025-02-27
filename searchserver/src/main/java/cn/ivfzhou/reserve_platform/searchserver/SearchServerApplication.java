package cn.ivfzhou.reserve_platform.searchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.reserve_platform")
@EnableEurekaClient
@EnableScheduling
public class SearchServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServerApplication.class, args);
    }

}
