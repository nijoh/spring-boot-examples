package com.nijo.example.web.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * 用于请求性能检测
 * 继承 HandlerInterceptorAdapter 过时
 * */
public class CoustomInterceptor implements HandlerInterceptor {

    private ThreadLocal<Long> map = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        map.set(System.currentTimeMillis());
        System.out.println("记录请求URL:" + request.getRequestURI() + "开始");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //结束请求时间
        Long end = System.currentTimeMillis() - (map.get());
        System.out.println("请求URL:"+request.getRequestURI()+",耗时："+end+"ms");
        map.remove();
    }
}
