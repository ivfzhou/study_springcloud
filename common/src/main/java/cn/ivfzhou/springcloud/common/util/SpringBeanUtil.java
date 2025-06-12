package cn.ivfzhou.springcloud.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    /**
     * 手动从 Spring 容器中获得元素。
     */
    public static <T> T getBean(Class<T> c) {
        return beanFactory.getBean(c);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringBeanUtil.beanFactory = beanFactory;
    }

}
