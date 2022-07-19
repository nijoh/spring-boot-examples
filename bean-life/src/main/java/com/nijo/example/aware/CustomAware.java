package com.nijo.example.aware;

import org.springframework.beans.factory.Aware;

/**
 * 自定义Aware接口 加入IOC
 * 实现初始化容器回调setCustomAware() 方法
 */
public interface CustomAware extends Aware {
    void setCustomAware(SpringAware springAware);
}
