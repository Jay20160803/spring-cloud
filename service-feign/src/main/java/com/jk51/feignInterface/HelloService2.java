package com.jk51.feignInterface;

import com.jk51.modle.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */

@FeignClient(value = "service-hi-2",fallback = HelloServiceFallback2.class )
public interface HelloService2 {


    /**
     *   测试Hytrix线程隔离
     * 1.在不使用Hytrix的情况下，通过压力测试软件，并发向service-hi-2、service-hi各发送10000个请求
     *
     * */

    @RequestMapping(value="hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name")String name);

    @RequestMapping(value = "hi2",method = RequestMethod.GET)
    User hi2(@RequestParam("name")String name, @RequestHeader("age")int age);

    @RequestMapping(value = "hi3",method = RequestMethod.GET)
    String hi3(@RequestBody User user);
}
