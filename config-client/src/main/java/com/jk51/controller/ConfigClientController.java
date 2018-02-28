package com.jk51.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${foo}")
    private String foo;

    @RequestMapping("getFoo")
    public String getFoo(){
        return foo;
    }
}
