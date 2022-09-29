package com.nijo.example.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(value = "com.nijo")
public class MiddleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiddleApplication.class, args);
    }
}
