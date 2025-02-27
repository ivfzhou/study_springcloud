package cn.ivfzhou.reserve_platform.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "cn.ivfzhou.reserve_platform.feign")
public class FeignConfiguration {
}
