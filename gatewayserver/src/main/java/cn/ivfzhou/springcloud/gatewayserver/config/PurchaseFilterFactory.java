package cn.ivfzhou.springcloud.gatewayserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

import cn.ivfzhou.springcloud.gatewayserver.filter.PurchaseFilter;

/**
 * 局部过滤器的工厂。
 */
@Component
public class PurchaseFilterFactory implements GatewayFilterFactory<PurchaseFilter> {

    @Autowired
    private PurchaseFilter purchaseFilter;

    @Override
    public Class<PurchaseFilter> getConfigClass() {
        return PurchaseFilter.class;
    }

    /**
     * 工厂返回的过滤器的对象。
     */
    @Override
    public GatewayFilter apply(PurchaseFilter config) {
        return purchaseFilter;
    }

    /**
     * 设置局部过滤器的名称。
     */
    @Override
    public String name() {
        return "purchaseFilter";
    }

}
