package com.jk51.feignInterface;


import org.springframework.stereotype.Component;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */
@Component
public class HelloServiceHystrix implements HelloService {

    @Override
    public String sayHiFromClientOne(String name) {
        return "hi,"+name+" sorry,error!";
    }
}
