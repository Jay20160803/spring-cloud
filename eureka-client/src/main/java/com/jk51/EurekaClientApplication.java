package com.jk51;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-01-23
 * 修改记录:
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaClientApplication {

    public static void main(String[] args){
        SpringApplication.run(EurekaClientApplication.class,args);
    }
}
