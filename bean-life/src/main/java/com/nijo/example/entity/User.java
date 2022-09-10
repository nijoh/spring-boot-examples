package com.nijo.example.entity;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用户pojo
 */
@Component
public class User implements InitializingBean {
    private String userName;

    public User() {
        System.out.println("User被初始化");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setUserName("2");
        System.out.println("afterPropertiesSet被执行:" + this.getUserName());
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("我是postConstruct方法执行...");
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
