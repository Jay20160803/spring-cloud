package com.jk51.controller;

import com.jk51.feignInterface.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;


    @RequestMapping(value = "hi",method = RequestMethod.GET)
    public String hi(@RequestParam String name){
        return helloService.sayHiFromClientOne(name);
    }
}
