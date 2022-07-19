package com.nijo.example.processor;

import com.nijo.example.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 用户pojo自定义后置处理器
 * 执行顺序 postProcessBeforeInitialization ---> InitializingBean ---> postProcessAfterInitialization
 * */
@Component
public class UserPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof User){
            ((User) bean).setUserName("1");
            System.out.println("UserPostProcessor>>后置处理器前置方法处理User:"+((User) bean).getUserName());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof User){
            ((User) bean).setUserName("1");
            System.out.println("UserPostProcessor>>后置处理器后置方法被调用:"+((User) bean).getUserName());
        }
        return bean;
    }

    /**
     * 存在多个BeanPostProcessor 实现类 想要某个先执行
     * 实现接口Ordered getOrder方法 返回值越小越先执行
     * */
//    @Override
//    public int getOrder() {
//        return -999;
//    }


}
