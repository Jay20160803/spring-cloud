服务注册与发现：
    eureka-service,eureka-clinet
    
负载均衡：
    rest+ribbon/接口注解+feign
    feign默认集成了ribbon，除了负载均衡，还
    
断路器：
    Hystrix，在ribbon或feign中集合Hystrix能解决，访问的某个服务不能提供服务时直接返回指定信息
    
