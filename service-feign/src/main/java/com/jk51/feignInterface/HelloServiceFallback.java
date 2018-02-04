package com.jk51.feignInterface;


import com.jk51.modle.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */
@Component
public class HelloServiceFallback implements HelloService {

    private Logger logger = LoggerFactory.getLogger(HelloServiceFallback.class);

    @Override
    public String sayHiFromClientOne(String name) {

        logger.error("记录请求fall到日志");

        return "hi,"+name+" sorry,error!";
    }

    @Override
    public User hi2(@RequestParam("name") String name, @RequestHeader("age") int age) {
        return new User("fail",age);
    }

    @Override
    public String hi3(@RequestBody User user) {
        return "hi,"+user.getName()+" sorry,error!";
    }
}
