package cn.ivfzhou.springcloud.purchaseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.springcloud")
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class PurchaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseServerApplication.class, args);
    }

}
