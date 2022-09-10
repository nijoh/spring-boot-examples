package com.nijo.example;

import com.nijo.example.entity.User;
import com.nijo.example.registered.BeanRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        BeanRegister.RootBeanDefinition();
        BeanRegister.GenericBeanDefinition();
    }
}
