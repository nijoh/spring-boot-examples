package com.nijo.example.web.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器加入Spring
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CoustomInterceptor())
                .addPathPatterns("/*");//拦截路径
        WebMvcConfigurer.super.addInterceptors(registry);
    }

//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new CoustomInterceptor())
//                .addPathPatterns("/*");//拦截路径
//        super.addInterceptors(registry);
//    }
}
