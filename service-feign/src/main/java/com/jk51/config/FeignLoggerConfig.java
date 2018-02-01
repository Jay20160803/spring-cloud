package com.jk51.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述: feign日志级配配置
 * 作者: gaojie
 * 创建日期: 2018-02-01
 * 修改记录:
 */
@Configuration
public class FeignLoggerConfig {

    /**
     * 对于Feign 的Logger级别主要有下面4 类， 可根据实际需要进行调整使用。
     • NONE: 不记录任何信息。
     • BASIC: 仅记录请求方法、URL以及响应状态码和执行时间。
     • HEADERS: 除了记录BASIC级别的信息之外， 还会记录请求和响应的头信息。
     • FULL: 记录所有请求与响应的明细， 包括头信息、请求体、元数据等。

     * */

    @Bean
    public Logger.Level feignLoggerLevel(){

        return Logger.Level.FULL;
    }
}
