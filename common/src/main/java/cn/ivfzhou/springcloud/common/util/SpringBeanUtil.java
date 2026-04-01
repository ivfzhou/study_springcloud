package cn.ivfzhou.springcloud.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * Spring Bean 工具类。
 * <p>
 * 实现 BeanFactoryAware 接口，在 Spring 容器初始化时保存 BeanFactory 引用，
 * 提供静态方法从 Spring 容器中获取 Bean 实例。
 * 适用于非 Spring 管理的类中需要获取 Spring Bean 的场景。
 * </p>
 */

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
