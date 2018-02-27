package com.jk51.controller;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: 重写异常处理controller
 * 作者: gaojie
 * 创建日期: 2018-02-27
 * 修改记录:
 */


@RestController
public class ErrorHandlerController implements ErrorController {



    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Object error(HttpServletRequest request){

        Map<String,Object> result = new HashMap<>();
        RequestContext ctx = RequestContext.getCurrentContext();
        if(ctx.size()==0){

            if(request.getAttribute("javax.servlet.error.status_code").toString().equals("404")){
                result.put("status",404);
                result.put("message","Not Found");
            }else{

                result.put("status",500);
                result.put("message","unkonwn zuul exception!");
            }

            return result;
        }
        ZuulException exception = findZuulException(ctx.getThrowable());

        result.put("status",exception.nStatusCode);
        result.putIfAbsent("message",exception.errorCause);
        return result;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            // this was a failure initiated by one of the local filters
            return (ZuulException) throwable.getCause().getCause();
        }

        if (throwable.getCause() instanceof ZuulException) {
            // wrapped zuul exception
            return (ZuulException) throwable.getCause();
        }

        if (throwable instanceof ZuulException) {
            // exception thrown by zuul lifecycle
            return (ZuulException) throwable;
        }

        // fallback, should never get here
        return new ZuulException(throwable, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null);
    }
}
