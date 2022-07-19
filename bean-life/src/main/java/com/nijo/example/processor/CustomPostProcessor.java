package com.nijo.example.processor;

import com.nijo.example.aware.CustomAware;
import com.nijo.example.aware.SpringAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * CustomAware 自定义Aware 放入IOC 实现容器回调set...()方法依赖
 * 后置处理器，底层源码依次注入BeanFactory、ApplicationContext等，那么会调用
 * 实现BeanPostProcessor所有子类的postProcessBeforeInitialization方法
 * 进行Bean注入调用set...方法
 */
@Component
public class CustomPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //这里必须要instanceof判断下 否则BeanFactory setBeanFactory也会进入这里来
        if (bean instanceof Aware) {
            if (bean instanceof CustomAware) {
                System.out.println("IOC容器中获取SpringAware并且set进去");
                ((CustomAware) bean).setCustomAware(beanFactory.getBean(SpringAware.class));
            }
        }
        return bean;
    }
}
