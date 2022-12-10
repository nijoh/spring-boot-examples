package com.example.beanaop.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class ProxyMain {

    public static void main(String[] args) {
        //初始化增强器
        Enhancer enhancer=new Enhancer();
        //设置回调接口 带入代理类
        enhancer.setCallback(new TaixProxy());
        //设置目标类
        enhancer.setSuperclass(TaixService.class);
        //创建增强
        TaixService proxy=(TaixService)enhancer.create();
        //开始调用
        proxy.checkOnTaix("小虎");
        proxy.checkDownTaix();
    }
}
