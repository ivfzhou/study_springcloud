package cn.ivfzhou.reserve_platform.event.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 手动将Bean注册到IOC容器中。获取到注册对象。
 * <p>
 * Spring -> IOC -> Bean工厂
 * Map<beanName, BeanDefinition>
 * Bean -> BeanDefinition
 */
@Component
public class SpringContextUtil implements BeanDefinitionRegistryPostProcessor {

    // 注册bean的核心对象。
    private BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     * 自定义工具方法 - 注册Bean对象
     */
    public void registerBean(String beanName, Object bean) {
        // 将bean封装成BeanDefinition对象。
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass(), (Supplier) () -> bean);
        // 将BeanDefinition注册到Spring容器中。
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition.getBeanDefinition());
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.beanDefinitionRegistry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

}
