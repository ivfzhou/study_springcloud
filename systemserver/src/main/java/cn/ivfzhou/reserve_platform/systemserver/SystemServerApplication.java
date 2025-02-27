package cn.ivfzhou.reserve_platform.systemserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "cn.ivfzhou.reserve_platform")
@EnableEurekaClient
public class SystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServerApplication.class, args);
    }

}
