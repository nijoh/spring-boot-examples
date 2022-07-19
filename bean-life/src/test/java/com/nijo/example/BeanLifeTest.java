package com.nijo.example;

import com.nijo.example.aware.SpringAware;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanLifeTest {
    @Autowired
    private SpringAware springAware;



    @Test
    public void SpringAwareTest(){
        System.out.println("name:"+springAware.getName());
        System.out.println("BeanFactory"+springAware.getBeanFactory());
        //是否同一对象
        boolean t=springAware.hashCode() == springAware.getBeanFactory().getBean("springAwareTest").hashCode();
        System.out.println(t);

    }

    @Test
    public void UserPostProcessorTest(){
        System.out.println("UserPostProcessorTest >> start");
    }
}
