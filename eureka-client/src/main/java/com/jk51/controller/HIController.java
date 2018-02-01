package com.jk51.controller;

import com.jk51.modle.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */
@RestController
public class HIController {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "hi",method = RequestMethod.GET)
    public String home(@RequestParam String name){


        //throw new NullPointerException("服务端发生异常");

        return "hi "+name+" ,i am from port: "+port;
    }

    @RequestMapping(value = "hi2",method = RequestMethod.GET)
    public User hi2(@RequestParam String name, @RequestHeader Integer age){


        //throw new NullPointerException("服务端发生异常");

        return new User(name,age);
    }

    @RequestMapping(value = "hi3",method = RequestMethod.POST)
    public String hi3(@RequestBody User user){


        //throw new NullPointerException("服务端发生异常");

        return "hi "+user.getName()+" ,i am from port: "+port;
    }
}
