package com.jk51.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.jk51.constants.ZuulConstant.KEY_ERROR;
import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:  所有的返回格式化，不适合web项目
 * 作者: gaojie
 * 创建日期: 2018-02-04
 * 修改记录:
 */
public class ResponseFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(ResponseFilter.class);


    @Override
    public boolean shouldFilter() {

        RequestContext context = RequestContext.getCurrentContext();

        // 有错误的返回不处理，其他请求都处理
        return null == context.get(KEY_ERROR);
    }

    @Override
    public Object run() {

        try {
            RequestContext context = getCurrentContext();

            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            context.setResponseBody("Modified via setResponseBody(): " + body);
            context.setResponseStatusCode(200);
        } catch (IOException e) {
            logger.error("response", e);
            rethrowRuntimeException(e);
        }
        return null;
    }

    @Override
    public String filterType() {
        logger.info("ResponseFilter==>filterType");
        return "post";
    }

    @Override
    public int filterOrder() {
        logger.info("ResponseFilter==>filterOrder");
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}
