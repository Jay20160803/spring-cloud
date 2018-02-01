package com.jk51.hystrixCommand;


import com.netflix.hystrix.*;
import org.springframework.web.client.RestTemplate;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 *
 * 描述: 传统继承方式实现Hystrix命令
 * 作者: gaojie
 * 创建日期: 2018-01-31
 * 修改记录:
 */
public class HiCommand extends HystrixCommand<String> {


    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");

    private RestTemplate restTemplate;
    private String name ;

    public HiCommand(RestTemplate restTemplate,String name) {

        //设置组名、命令名、线程池名
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("groupKey"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("hiforCommandCommandKey"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("hiforCommandThreadPoolKey")));
        this.restTemplate = restTemplate;
        this.name = name;
    }

    @Override
    protected String run() throws Exception {

        return restTemplate.getForObject("http://service-hi/hi?name="+name,String.class);

    }


    /**
     * 服务降级逻辑
     * */
    @Override
    protected String getFallback(){

        Throwable throwable = getExecutionException();

        return "sorry server error! cause: "+throwable;
    }


    /**
     * 开启缓存
     *
     * 当参数name相同时，返回缓存的数据
     * */
    @Override
    protected String getCacheKey(){
        return name;
    }
}
