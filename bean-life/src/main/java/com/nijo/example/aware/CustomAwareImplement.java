package com.nijo.example.aware;

import org.springframework.stereotype.Component;

/**
 * 自定义Aware 实现类
 */
@Component
public class CustomAwareImplement implements CustomAware {

    @Override
    public void setCustomAware(SpringAware springAware) {
        System.out.println("自定义Aware获取名称:" + springAware.getName());
    }
}
