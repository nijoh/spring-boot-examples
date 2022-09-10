package com.nijo.example.entity;

import lombok.Data;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

@Data
public class BeanEntity implements InitializingBean {

    private String name;

    private String parentName;

    private int age;

    public BeanEntity() {
        System.out.println("BeanEntity被实例化");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet 初始化开始修改数据");
        setName("afterPropertiesSet");
    }
}
