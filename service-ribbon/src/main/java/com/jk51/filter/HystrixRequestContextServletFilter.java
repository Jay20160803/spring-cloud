package com.jk51.filter;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 通过servlet的Filter配置hystrix的上下文。
 * 作者: gaojie
 * 创建日期: 2018-01-31
 * 修改记录:
 */
@WebFilter(filterName = "hystrixRequestContextServletFilter",urlPatterns = "/*",asyncSupported = true)
@ServletComponentScan
public class HystrixRequestContextServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try{
            chain.doFilter(request,response);
        }finally {
            context.shutdown();
        }
    }

    @Override
    public void destroy() {

    }
}
