服务注册与发现：
    eureka-service: 服务注册中心
    eureka-clinet:任何服务都可以作为eureka-clinet,并注册到eureka-service中
    
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
    
    服务过滤：继承ZuulFilter类
    
分布配置中心（Spring cloud config）

    在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client。
    
    config server: 供有需要的服务读出git中的配置文件信息
    
    config client：任何服务都可以作为config client，使其能想读本地的配置文件一样读取分布配置中心（git）的配置信息


消息总线（Spring Cloud Bus）：
    
    Spring Cloud Bus 将分布式的节点用轻量的消息代理连接起来。它可以用于广播配置文件的更改或者服务之间的通讯，也可以用于监控。本文要讲述的是用Spring Cloud Bus实现通知微服务架构的配置文件的更改。
    
服务链路追踪（Spring cloud Sleuth）:
    
    Spring Cloud Sleuth 主要功能就是在分布式系统中提供追踪解决方案，并且兼容支持了 zipkin，你只需要在pom文件中引入相应的依赖即可
 
高可用服务注册中心    
    
    Eureka通过运行多个实例，使其更具有高可用性。事实上，这是它默认的属性，你需要做的就是给对等的实例一个合法的关联serviceurl。
    
    
什么是微服务

    微服务是系统架构上的一种风格，它的主旨是将一个原本独立的系统拆分成多个小型服务，这些小型服务都在各自独立的进程中运行，服务之间基于
    HTTP的RESTful API 进行通信协作。被拆分成的每一个小型服务都围绕这系统中的某一项或一些耦合度较高的业务功能进行构建，
    并且每个服务都维护这自身的数据储存、业务开发、自动化测试案例以及独立部署机制。由于有了轻量级的通信协作基础，所以这些微服务
    可以使用不同的语言编写
    
