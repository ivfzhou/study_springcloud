package cn.ivfzhou.reserve_platform.purchaseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.reserve_platform")
@EnableEurekaClient
@EnableScheduling
public class PurchaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseServerApplication.class, args);
    }

}
