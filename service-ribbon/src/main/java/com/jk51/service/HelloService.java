package com.jk51.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *添加断路
     * 依赖线程池隔离--------通过依赖服务实现线程池隔离，可以让我们的应用更加健壮，不会因为个别依赖服务出现问题而引起非相关服务的异常（前提需要修改使用指定的连接线程池）
     *
     * */


    /**
     * Hystrix 同步执行服务请求
     * 设置命令的组名、命令名、线程池名
     *
     * 属性设置--请求超时时间设置为5秒
     * */
    @HystrixCommand(fallbackMethod = "hiError",groupKey = "helloGroup",commandKey = "hi",threadPoolKey = "hiThread",
        commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
        })
    public String hi(String name){

        return restTemplate.getForObject("http://service-hi/hi?name="+name,String.class);
    }


    /**
     * Hystrix 异步执行服务请求
     *
     * */
    @HystrixCommand(fallbackMethod = "hiError")
    public Future<String> hiAsync(String name){

        return new AsyncResult<String>(){

            @Override
            public String invoke(){
                return restTemplate.getForObject("http://service-hi/hi?name="+name,String.class);
            }
        };

    }


    /**
     * Throwable e 触发服务降级的具体异常
     *
     * */
    private String hiError(String name,Throwable e){

        return "hi,"+name+" sorry,error! cause: "+e.toString();
    }
}
