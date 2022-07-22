package com.nijo.example.web.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //自定义过滤器
        registrationBean.setFilter(new CustomFilter());
        //过滤/*所有路径
        registrationBean.addUrlPatterns("/*");
        //初始化数据 可以在dofilter()逻辑过滤不判断url
        //registrationBean.addInitParameter("noFilter","/1,/2");
        registrationBean.setName("customFilter");
        //存在多个过滤器 设置优先级
        registrationBean.setOrder(0);
        return registrationBean;
    }
}
