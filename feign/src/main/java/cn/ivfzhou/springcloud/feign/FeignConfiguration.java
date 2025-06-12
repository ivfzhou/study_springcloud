package cn.ivfzhou.springcloud.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "cn.ivfzhou.springcloud.feign")
public class FeignConfiguration {
}
