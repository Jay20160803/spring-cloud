package com.jk51.filters;

import com.jk51.util.ResponseHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletResponse;

import static com.jk51.constants.ZuulConstant.KEY_ERROR;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-02-04
 * 修改记录:
 */
public class ErrorFilter extends ZuulFilter {

    protected static Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    private ResponseHandler errorHandler;

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }


    //在SendErrorFilter之前执行
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        try{

            RequestContext context = RequestContext.getCurrentContext();
          /*  context.getResponse().setCharacterEncoding("utf-8");
            context.getResponse().setContentType("application/json; charset=utf-8");*/


            logger.error("zuul error",context.getThrowable());

            if(errorHandler != null){

                context.setResponseStatusCode(errorHandler.getResponseCode());
                String body = errorHandler.getResponseBody(null,context.getThrowable());
                context.setResponseBody(body);

            }else {

                context.setResponseStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                context.setResponseBody(context.getThrowable().getMessage());
            }

            context.remove("throwable");   //移除掉后SendErrorFilter就不会执行了
            context.put(KEY_ERROR, true);

        }catch (Exception e){

            rethrowRuntimeException(e);
        }
        return null;
    }

    public ResponseHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ResponseHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

}
