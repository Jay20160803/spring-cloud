package com.jk51.controller;

import com.jk51.service.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


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


    @RequestMapping(value = "hiAsync",method = RequestMethod.GET)
    public String hiAsync(@RequestParam String name){

        Future<String> future =  helloService.hiAsync(name);

        while (!future.isDone()){
            System.out.println("curentTime: "+System.currentTimeMillis());
        }
        try {
            return future.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "aa";
    }


    @RequestMapping(value = "hi",method = RequestMethod.GET)
    public String hi(@RequestParam String name){

        return helloService.hi(name);
    }

}
