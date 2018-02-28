package com.jk51.controller;

import com.jk51.feignInterface.HelloService;
import com.jk51.feignInterface.HelloService2;
import com.jk51.modle.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    private HelloService helloService;
    @Autowired
    private HelloService2 helloService2;


    @RequestMapping(value = "hi",method = RequestMethod.GET)
    public String hi(@RequestParam String name){

        logger.info(" call trace 1");
        return helloService.sayHiFromClientOne(name);
    }

    @RequestMapping(value = "hitest",method = RequestMethod.GET)
    public String hitest(@RequestParam String name){

        return helloService2.sayHiFromClientOne(name);
    }

    @RequestMapping(value = "hi2",method = RequestMethod.GET)
    public User hi2(@RequestParam String name){

        return helloService.hi2(name,20);
    }

    @RequestMapping(value = "hi3",method = RequestMethod.GET)
    public String hi3(@RequestParam String name){

        return helloService.hi3(new User("aa",22));
    }
}
