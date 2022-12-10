package com.example.beanaop.cglib;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TaixProxy implements MethodInterceptor{
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("欢迎选乘服务");
        Object result = methodProxy.invokeSuper(o, objects);
        //method.invoke(new TaixService(),objects);
        System.out.println("下次欢迎光临");
        return result;
    }
}
