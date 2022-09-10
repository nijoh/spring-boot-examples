package com.nijo.example.registered;

import com.nijo.example.entity.BeanEntity;
import com.nijo.example.entity.BeanEntityParent;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

public class BeanRegister {

    public static void RootBeanDefinition() {
        //所有IOC容器Bean注册信息都是存放beanDefinitionMap中
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //BeanDefinition子接口RootBeanDefinition 也可以用new RootBeanDefinition()
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(BeanEntity.class);
        beanDefinitionBuilder.addPropertyValue("name", "nijo");
        beanDefinitionBuilder.setScope(SCOPE_SINGLETON);
        //注册
        beanFactory.registerBeanDefinition("beanEntity", beanDefinitionBuilder.getBeanDefinition());
        //获取并实列化
        BeanEntity result = (BeanEntity) beanFactory.getBean("beanEntity");
        System.out.println(result);

    }

    public static void GenericBeanDefinition() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //父类注册器
        BeanDefinitionBuilder parent = BeanDefinitionBuilder.genericBeanDefinition(BeanEntityParent.class);
        //设置父类属性值
        parent.addPropertyValue("parentName", "parentBeanEntity");
        parent.addPropertyValue("age", 1);
        beanFactory.registerBeanDefinition("beanEntityParent", parent.getBeanDefinition());

        //子类注册器
        BeanDefinitionBuilder child = BeanDefinitionBuilder.genericBeanDefinition(BeanEntity.class);
        //设置属性值
        child.addPropertyValue("name", "child");
        //设置继承关系
        child.setParentName("beanEntityParent");
        beanFactory.registerBeanDefinition("beanEntity", child.getBeanDefinition());

        BeanEntityParent parentBean = (BeanEntityParent) beanFactory.getBean("beanEntityParent");
        System.out.println("parentBean" + parentBean);
        BeanEntity childBean = (BeanEntity) beanFactory.getBean("beanEntity");
        System.out.println("childBena:" + childBean);
    }
}
