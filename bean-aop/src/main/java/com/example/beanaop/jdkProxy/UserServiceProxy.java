package com.example.beanaop.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//代理类
public class UserServiceProxy implements InvocationHandler {
    //目标类
    private Object target;

    public UserServiceProxy(Object target) {
        this.target = target;
    }

    //invoke 负责调用
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行前操作....");
        Object result = method.invoke(target, args);
        System.out.println("执行后操作....");
        return result;
    }
}
