package com.nijo.example.web.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义Filter过滤器
 * 可以用作请求前 信息头验证（没有token、或自定义头）、拦截xss、SQL注入
 * 这里提供2中方式注入 1.@webFilter() + @ServletComponentScan
 * 2.Bean java config模式
 */
public class CustomFilter implements Filter {

    /**
     * 项目启动时候值会运行一次
     * 可以用作当前数据初始化
     * eg init add  List<string>
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CustomFilter 初始化init（）");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //可以做信息头的检测、xss检测、sql注入、记录请求
        System.out.println("start check RequestHeard.....");
        String token = ((HttpServletRequest) servletRequest).getHeader("token");
        if(Objects.isNull(token)||"".equals(token)){
            //参数有误
            servletRequest.getRequestDispatcher("/parameterException").forward(servletRequest,servletResponse);
        }else {
            //符合条件请求
            filterChain.doFilter(servletRequest,servletResponse);
        }
        System.out.println("返回response 过滤参数");
    }

    /**
     * 项目停止会调用该方法
     * */
    @Override
    public void destroy() {
        System.out.println("CustomFilter 销毁 destroy（）");
    }
}
