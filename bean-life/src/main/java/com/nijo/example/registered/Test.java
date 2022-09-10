package com.nijo.example.registered;

import com.nijo.example.entity.BeanEntity;
import com.nijo.example.entity.BeanEntityParent;
import com.nijo.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
public class Test {
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    public void t1(){
        BeanDefinitionBuilder parent = BeanDefinitionBuilder.genericBeanDefinition(BeanEntityParent.class);
        parent.addPropertyValue("parentName", "parentBeanEntity");
        parent.addPropertyValue("age", 1);
        defaultListableBeanFactory.registerBeanDefinition("beanEntityParent", parent.getBeanDefinition());

        BeanEntityParent bean = defaultListableBeanFactory.getBean(BeanEntityParent.class);
        System.out.println("BeanEntityParent"+bean);
        //获取启动时其他Bean
        User user = defaultListableBeanFactory.getBean(User.class);
        System.out.println("user:"+user);
    }
}
