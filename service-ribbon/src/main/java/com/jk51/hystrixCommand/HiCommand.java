package com.jk51.hystrixCommand;


import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-31
 * 修改记录:
 */
public class HiCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;
    private Long id ;

    protected HiCommand(Setter setter,RestTemplate restTemplate,Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        //return restTemplate.getForObject("http://service-hi/hi?name="+name,String.class);
        return "aa";
    }
}
