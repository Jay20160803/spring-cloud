package com.jk51.config;

import com.google.gson.Gson;
import com.jk51.filters.ErrorFilter;
import com.jk51.filters.ResponseFilter;
import com.jk51.modle.ResponseEntity;
import com.jk51.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-02-04
 * 修改记录:
 */
@Configuration
public class ZuulFilterConfig {

    @Bean
    public ErrorFilter errorFilter(CustomErrorHandler errorHandler) {
        ErrorFilter errorFilter = new ErrorFilter();
        errorFilter.setErrorHandler(errorHandler);
        return errorFilter;
    }
/*
    @Bean
    public ResponseFilter responseFilter(){
        return new ResponseFilter();
    }*/



    /**
     * zuul 错误处理
     *
     * @author cml
     *
     */
    @Component
    public class CustomErrorHandler implements ResponseHandler {


        private String errorMessage = "接口调用异常";

        @Override
        public int getResponseCode() {
            return HttpServletResponse.SC_OK;
        }

        @Override
        public String getResponseBody(String originMessage, Throwable e) {
            Gson gson = new Gson();
            return gson.toJson(ResponseEntity.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,errorMessage));
        }
    }
}
