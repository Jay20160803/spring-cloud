package com.jk51.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:  从spring cloud config 中读取zuul的配置信息
 * 作者: gaojie
 * 创建日期: 2018-02-04
 * 修改记录:
 */

@Configuration
public class ZuulPropertiesConfig {


    @Bean
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties(){
        return new ZuulProperties();
    }
}
