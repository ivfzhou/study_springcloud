package cn.ivfzhou.reserve_platform.gatewayserver.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    /**
     * 手动从Spring容器中获得元素。
     */
    public static <T> T getBean(Class<T> c) {
        return beanFactory.getBean(c);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

}
