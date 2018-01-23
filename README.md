服务注册与发现：
    eureka-service,eureka-clinet
    
负载均衡：
    rest+ribbon/接口注解+feign
    feign默认集成了ribbon，除了负载均衡，还
    
断路器：
    Hystrix，在ribbon或feign中集合Hystrix能解决，访问的某个服务不能提供服务时直接返回指定信息
    断路打开后，可用避免连锁故障，fallback方法可以直接返回一个固定值
    
路由网关(Zuul):
    Zuul的主要功能是路由转发和过滤器，zuul默认和Ribbon结合实现了负载均衡
    zuul有以下功能：
    Authentication
    Insights
    Stress Testing
    Canary Testing
    Dynamic Routing
    Service Migration
    Load Shedding
    Security
    Static Response handling
    Active/Active traffic management