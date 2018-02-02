服务注册与发现：
    eureka-service: 服务注册中心
    eureka-clinet:任何服务都可以作为eureka-clinet,并注册到eureka-service中
    
客户端负载均衡：

    rest+ribbon/接口注解+feign
    feign默认集成了ribbon，除了负载均衡，还
    利用他对RestTemplate的请求拦截来实现对依赖的接口调用，而ResetTemplate已经实现了对HTTP请求的封装处理，形成了一套模板化的调用方式
    
断路器：

    Hystrix，在ribbon或feign中集合Hystrix能解决，访问的某个服务不能提供服务时直接返回指定信息
    断路打开后，可用避免连锁故障，fallback方法可以直接返回一个固定值
    
    有一些情况可以不去实现降级逻辑：
       1. 执行写操作的命令
       2. 执行批处理或离线计算的命令
       不论Hystrix 命令是否实现了服务降级，命令状态和断路器状态都会更新，并且我们可以由此了解到命令执行的失败情况
    
    同步调用服务/异步调用服务（实现查看server-ribbon项目，HelloService类）
    
    依赖服务实现线程池隔离：
        默认是开启的，我没可以设置其参数
        
    
    异常处理：
        
        1. 异常传播
            @HystrixCommand(ignoreExceptions = {BadRequestException.class})
            当方法抛出类型为BadRequestException的异常时，Hystrix会将它包装在HystrixBadRequestException中抛出，这样就不会触发后续的fallback逻辑
            
        2. 异常处理
            当Hystrix命令因为异常（除了HystrixBadRequestException的异常）进入服务降级逻辑之后，往往需要对不同的异常做针对性的处理
            在fallback实现方法的参数中增加Throwable对象的定义，这样在方法内部就可以获取服务降级的具体异常内容
 
 
    命名名称、分组以及线程池划分：
        
        通过设置命令组,Hystrix会根据组来组织和统计命令的告警、仪表盘等信息，Hystrix命令默认的线程划分也是根据命令分组来实现的。
        
        只需设置@HystrixCommand 注解的commandKey、groupKey 以及threadPoolKey 属性即可， 它们分别表示了命令名称、分组以及线程池划分，
        
     
    请求缓存：
        
        在分布式环境下，通常压力来自于对依赖的调用，因为请求依赖服务的资源需要通过通信来实现，Hystrix中提供类请求缓存的功能
        
        1.开启请求缓存功能
        
    
    请求合并：
        
        由于请求合并器的延迟时间窗口会带来额外的开销，所以我们是否使用请求合并器需要根据依赖服务调用的实际情况选择，主要考虑下面两个方面：
            1. 请求命令本身的延迟。如果依赖服务的请求命令本身是一个高延迟的命令，那么可以使用请求合并器
            2. 延迟时间窗内的并发量。如果一个时间窗内只有1-2个请求，那么这样的依赖服务不适合使用请求合并器
    
    属性详解：
        
        属性的优先级：
            
            1. 全局默认值：如果没有设置下面三个级别的属性，那么这个属性就是默认值
            2. 全局配置属性：通过在配置文件中定义全局属性值，在应用启动时或在于Spring config和Spring Cloud bus实现的动态刷新配置功能配合下，
                可以实现对“全局默认值”的覆盖以及在运行期对“全局默认值”的动态调整
            3. 实例默认值：通过代码为实例定义的默认值
            4. 实例配置属性： 通过配置文件来为指定的实例进行属性配置
            
        Command属性：
            
            1. execution配置（控制HystrixComand.run()的执行）
                execution.isolation.strategy: 设置隔离策略，默认是THREAD（线程池隔离策略）
                execution.isolation.thread.timeoutInMilliseconds: 设置执行的超时时间，默认是（1秒）
                execution.timeout.enabled: 该属性用来配置执行时是否启用超时时间，默认为 true
                execution.isolation.thread.interruptOnTimeout: 该属性用来配置超时的时候是否要中断
            2. fallback配置（控制getFallback()的执行）
                fallback.isolation.semaphone.maxConcurrentRequests: 设置从调用线程中允许getFallback方法执行的最大并发请求数，默认为10 
                fallback.enabled: 设置服务降级策略是否启用，默认为true
            3.circuitBreaker设置（控制HystrixCircuitBreaker的行为）
            4.threadPool属性
                coreSize: 设置执行命令线程池的核心线程数，该值也是命令执行的最大并发量，默认值为10
                maxQueueSize: 设置线程
        Hystrix 仪表盘：
        
        需要测试下线程隔离:
           不知道怎么测，用压力测试工具测试是只能保证开启

声明式服务调用：Spring Cloud Feign
                
     基于Netfix实现，整合了Spring Cloud Ribbon与Spring Cloud Hystrix,除了提供了两者的功能之外，它还提供了一种申明式的Web服务客户端定义方式
     
     参数绑定：
        支持Spring MVC 常用的参数绑定方式，具体看service-feign代码
     继承特性：
        没什么使用价值，添加复杂度
     Ribbon配置：
        全局配置：
            直接使用ribbon.<key>=<value>的方式来配置ribbon的各项默认参数
        指定服务配置：
            <client>.ribbon.key=value, client是在使用@FeignClient(value="HEELO_SERVICE")来创建Feign客户端的时候，同时也创建一个名为HELLO-SERVICE的Ribbon客户端
     重试机制：
     
     Hystrix配置：
        
        
     服务降级配置：
         实现@FeignClient注解的接口，实现的方法为对应的服务降级逻辑，并在@FeignClient通过fallback指向该class类，具体看service-feign代码
     其他配置：
         请求压缩：
            Spring Cloud Feign支持对请求与响应进行GZIP压缩，以减少通讯过程中的性能损耗
              #请求与响应压缩
              feign:
                  compression:
                    request:
                      mime-types: text/xml,application/xml,application/json   #压缩的请求类型
                      min-request-size: 2048  #请求超过指定大小才压缩
                      enabled: true
                    response:
                      enabled: true
         日志配置：
            查阅Spring Cloud官方文档后，需要先配置一个Bean,设定Feign输出的日志内容。
                @Bean
                public Logger.Level feignLoggerLevel() {
                    return feign.Logger.Level.FULL;
                }
                
            Level有好几种，我选择的是FULL。
            其次，在application.properties中要设定一行这样的代码：
            logging.level.<你的feign client全路径类名>: DEBUG

     
     
     
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
    
