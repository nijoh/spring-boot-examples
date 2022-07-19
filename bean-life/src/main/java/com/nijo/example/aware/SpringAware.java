package com.nijo.example.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;


/**
 * 自定义Aware类
 * 实现BeanNameAware、BeanFactoryAware
 */
@Component
public class SpringAware implements BeanNameAware, BeanFactoryAware {
    private BeanFactory beanFactory;

    private String name;

    public SpringAware() {
        System.out.println("SpringAwareTest 无参数构造初始化");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getName() {
        return name;
    }
}
