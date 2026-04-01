package cn.ivfzhou.springcloud.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Feign 客户端配置类。
 * <p>
 * 通过 @EnableFeignClients 注解启用 Feign 远程调用功能，
 * 并指定扫描 cn.ivfzhou.springcloud.feign 包下的所有 Feign 接口。
 * </p>
 */
