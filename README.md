什么是微服务：


    微服务是架构上的一种设计风格，它的主旨是将一个原本独立的系统分成多个小型服务，这些小型服务之间都在独立的进程中运行，服务之间通过基于
    HTTP的RESTful API进行通讯协作。被拆分成的每一个小型服务都围绕着系统中的某一项或一些耦合度较高的业务功能进行构建，并且每个服务都维护
    着自身的数据存储、业务开发、自动化测试案例以及独立的部署机制。由于基于轻量级的通讯协议基础，所以这些服务可以使用不同的语言来编写

与单体系统的区别：


    单体系统的业务逻辑在一个应用中，开发、测试、部署都还比较容易且方便，但是，随着企业的发展，系统为了应对不同的业务需求不断为该为该单体项目
    增加不同的业务模块，不断扩大的需求会使得单体应用变得越来越臃肿。单体应用的问题就逐渐凸显出来，由于单体系统部署在一个进程内往往我们修改了
    一个很小的功能，为了部署上线会影响其他功能的运行。并且单体应用中的这些功能模块的使用场景、并发量、消耗的资源类型各有不同，对于资源的利用
    又互相影响，这样使得我们对各个业务模块的系统容量很难给出较为准确的评估。所以，单体系统在初期虽然可以非常方便的进行开发和使用，但是随着系统
    的发展，维护成本会变得越来越大，且难以控制。
    
    为了解决单体系统变得庞大臃肿之后产生的难以维护的问题，微服务架构诞生了并被大家所关注。我们将系统中的不同功能模块拆分成多个 不同的服务，这
    些服务都能独立部署和扩展。由于每个服务运行在自己的进程内，在部署上有稳固的边界，这样每个服务的更新都不会影响其他服务的运行。同时，由于是
    独立部署的，我们可以更准确地为每个服务评估性能容量，通过配合服务间的协作流程也可以更容易的发现系统的瓶颈位置，以及给出较为准确的系统性能
    容量评估。
    
如何实施微服务：


    运维的挑战：  
        在微服务架构中，运维人员需要维护的进程数量会大大的增加，运维过程需要更多的自动化
        解决方案：docker + k8s
        
    接口的一致性： 
        虽然我们拆分了服务，但是业务逻辑上的依赖并不会消除，只是从单体应用中的代码依赖变为了服务间的通讯依赖。而当我们对现有接口进
        行了一些修改，那么交互方也需要协调这样的改变来进行发布，以保证接口的正确调用。我们需要更完善的接口和版本管理，或是严格地遵循开闭原则。
        解决方案：严格的遵循开闭原则
        接口中改变一般是请求参数和返回结果的改变
        返回结果使用统一的数据结构
        
    分布式的复杂性：
        由于拆分后的各个微服务独立部署并运行在各自的进程内，它们只能通过通信来进行协作，所以分布式环境的问题都是微服务架构系统设计时需要考虑的
        重要因素，比如网络延迟、分布式事务、异步消息等
        
微服务的九大特性：


    服务组件化、按业务组织团队、做产品的态度
    智能端点与哑管道：
        在微服务架构中，通常会使用一下两种服务调用方式：
            一：使用HTTP的RESTful API或轻量级的消息发送协议，实现信息传递与服务调用的触发
            二：通过在轻量级消息总线上传递消息，类似RabbitMQ等一些提供可靠异步交换的中间件
            
    去中心化治理      
    去中心化处理数据
    基础设施自动化：    自动化测试、自动化部署
    容错设计
    演进设计
    
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
        1.开启重试，并配置对应的参数
        2.导入Retry包
        详细查询Ribbon代码
     Ribbon设置：
        看实例
     Hystrix配置：
        看实例
        
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

    在使用微服务的时候，微服务的实例会不断的变化，按以往通过修改nginx的conf文件来实现微服务实例的变化会越来越难，所以我们需要一套机制来有效降低
    维护路由规则与服务实例聊表的难度----通过面向服务的路由配置方式，我们不需要再为各个路由维护服务应用的具体实例的位置，而是通过简单的path与serviceId的映射组合，使得维护工作变得非常简单。
    这完全归功于Spring Cloud Eureka的服务发现机制，它使得API网关服务可以自动化完成服务实例清单的维护，完美的解决了对路由映射实例的维护问题
    
    
    其次，我们需要保证对外服务的安全性，我们在服务端实现的微服务接口，往往都会有一定的权限校验机制，比如对用户登录状态的校验，权限校验，由于使用微服务
    架构的理念，我们将原本处于一个应用中的多个模块拆成了多个应用，但是这些应用提供的接口都需要这些校验逻辑。我么也需要一套机制能够很好地解决微服务架构中，
    对于微服务接口访问时各前置校验的冗余问题
    
    为类解决以上问题，API网关的概念应运而生，它的定义类似于面向对象设计模式中的外观模式
    实现的功能：
        调度
        过滤
        请求路由、负载均衡   依赖ribbon实现
        校验过滤
        与服务治理框架的结合
        请求转发时的熔断机制  依赖Hystrix实现
        服务的聚合
        
    
    服务过滤：继承ZuulFilter类
    
    路由详解：
        服务路由的默认规则：
            当我们为Zuul构建的API网关服务引入Eureka之后，它为Eureka中的每个服务都自动创建一个默认的路由规则，这个默认规则
            的path会使用serviceId配置的服务名作为前缀（/routes断点来返回当前的所有路由规则）
            我们可以使用zuul.ignored.services参数来设置一个服务名匹配表达式来定义不自动创建路由的规则（设置看applicaiton.yaml）
        自定义路径映射的规则：
        路径匹配：
            多个路径都匹配是，zuul选择配置文件中第一个匹配的路径
        忽略表达式：
        本地跳转：
        Cookie与头信息：
            zuul在请求路由时，会过滤掉HTTP请求头信息中的一些敏感信息，默认过滤设置（zuul.sensitiveHeader=Cookie、Set-Cookie、Authorization）,
            如果我们使用Spring Security、Shiro等安全框架构建Web应用，由于cookie无法传递，我们将无法实现登录和鉴权，解决方案：
                ＃方法一：对指定路由开启自定义敏感头
                zuul.routes.<router>.customSensitiveHeaders=true
                ＃方法二：将指定路由的敏感头设置为空
                zuul.routes.<router>.sensitiveHeaders=
                
                试了下方法一没起作用，方法二可以
         重定向问题：
            服务在跳转到URL的时候，跳转到的是服务注册到Eureka中的实例地址，不是通过网关获取到的路由地址，解决方案：
                zuul.addHostHeader=true
            
         Hystrix和Ribbon支持：
            
            zuul包含了对hystrix,ribbon的依赖，所以zuul天生具有线程隔离和断路器的保护，以及对服务调用的客户端负载均衡。但是需要注意的是
            当使用path与url的映射关系来配置路由规则时，对于路由转发的请求不会采用HystrixCommand来包装，所以这类路由请求没有线程隔离和
            断路器的保护，并且也不会有负载均衡的能力，因此我们在使用zuul的时候尽量使用path和serviceId的组合来进行配置
            我们可以通过设置hytrix和ribbon的参数来调整路配置
            
         过滤器详解：
            
           zull包含了对请求的路由和过滤两个功能，其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一路口的基础；而过滤器则负责对请求的处理过程进行
           干预，是实现请求校验、服务聚合等功能的基础。然而实际上，路由功能在真正运行时，它的路由映射和请求转发都是由几个不同的过滤器完成的。其中，路由映射主要通过pre
           类型的过滤器完成，它将请求路劲与配置的路由规则进行匹配，以找到需要转发的目标地址；而请求转发的部分则是由rote类型的过滤器完成，对pre类型获得的路由地址进行转发。
           所以，过滤器可以说是zuul实现API网关功能最为核心的部件
         
            filterType: pre(请求路由之前调用)、routing(在路由请求时被调用)、post(在routing和error过滤器之后被调用)、error(处理请求时发生错误时被调用)
            
            核心过滤器：
                为了让API网关组件可以被更方便地使用，zull在HTTP请求生命周期的各个阶段默认实现了一批核心过滤器，他们会在API网关服务启动的时候被
                自动加载和启用
                
            异常处理与异常指定格式返回：
            
                Error类型的过滤器处理完毕后，除了来自post阶段的异常之外，都会被post过滤进行处理
                
                自定义异常处理（微服务实战和网上的一些资料提供的是旧版本zuul的解决方案，测试了下新版zuul的异常处理过滤器是够用的，在需要自定义异常信息和返回的数据格式时我们可以做响应的重写，比自定义异常处理过滤器更简单，也更全面（ps:自定义异常过器时很容易遇到第一点的问题））
                依然使用SendErrorFilter异常过滤器
                自定义异常信息：重写DefaultErrorAttributes中getErrorAttributes方法的返回
                自定义异常的返回数据格式：重写ErrorController

            禁用过滤器：
                zuul.<SimpleClassName>.<filterType>.disable=true
                
         动态加载： 
               动态路由:
                    1.zuul服务配置连接spring cloud config
                    2.配置ZuulPropertiesConfig
                    3.修改spring cloud config 的配置提交后，使用post类型访问zuul服务的 /refresh 刷新配置
                    注意事项：
                        1.git中的配置文件中如果配置了不存在的服务，调用zull的 /routes和 /refresh都会报错
                        2.zuul的applicaiton.name 需要与配置文件同名，可指定profile
               动态过滤器：
                     通过动态语言实现
                     
                     
                    
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
    
  
    
分布配置中心（Spring cloud config）

    在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client。
    config server: 供有需要的服务读出git中的配置文件信息 
    config client：任何服务都可以作为config client，使其能想读本地的配置文件一样读取分布配置中心（git）的配置信息

    当无法从github clone配置信息到本地时，会记录一下日志 “Could not fetch remote for master remote: https://github.com/Jay20160803/spring-cloud-config.git”
    但config-server会返回git在本地存储的配置信息

    Config Server巧妙地通过git clone将配置信息存于本地， 起到了缓存的作用， 即使当Git服务端无法访问的时候， 依然可以取ConfigServer中的缓存内容进行使用。
    
    服务端详解：
        Git配置仓库：
            占位符配置URI：
                我们可以通过{application}占位符来实现一个Git仓库目录的配置效果,{application}代表应用的名字，所以客户端应用
                向Config server发起获取配置请求的时，Config server会根据客户端的spring.application.name信息来填充{application}占位符以定位配置资源位置，从而
                实现根据微服务应用的属性动态获取不同位置的配置
                spring.cloud.config.server.git.uri=http://git.oschina.net/didispace/{application}
            配置多个仓库：
        属性覆盖：
        安全保护：(测试没能成功)
            使用spring-security，在服务中引入secrity start依赖，在配置文件中设置用户名和密码，客户端在链接配置中心时需要配置对用的安全信息
            security.user.name=user
            security.user.password=123456
        加密解密：
        高可用：
            将Config server当服务启动多个节点，使用Feign实现负载均衡调用，细节查看客户端详解
    
    客户端详解：
        URI指定配置中心：
            SpringC loudC onfig的客户端在启动的时候，默认会从工程的classpath中加载配置信
            息并启动应用。只有当我们配置spring.cloud.config.uri 的时候， 客户端应用才会
            尝试连接SpringCloud Config的服务端来获取远程配置信息并初始化Spring环境配置
         服务化配置中心：
             将config server注册到服务注册中心后，为config client添加注册发现，在配置中做以下配置不需要在配置config server的URI
             spring.cloud.config.discovery.enabled=true
             spring.cloud.config.discovery.serviceid=config-server
             spring.cloud.config.profile=dev
    动态刷新：
           注意：@RefreshScope需要标记在对应的变量类上

             
消息总线（Spring Cloud Bus）：
    
    Spring Cloud Bus 将分布式的节点用轻量的消息代理连接起来。它可以用于广播配置文件的更改或者服务之间的通讯，也可以用于监控。本文要讲述的是用Spring Cloud Bus实现通知微服务架构的配置文件的更改。
    
    添加消息总线（整合RabbitMq）:
        1-3客户端服务端中都需要配置
        1.在配置文件中配置RabbitMq的地址，账户密码
        2.引入spring-cloud-starter-bus-ampq的包
        3.开启消息跟踪
        4.客户端在对应的变量类上添加@RefreshScope
        5.通过向服务端发送/bus/refresh 刷新所有连接rabbitMq客户端的配置信息
        
    其他消息代理的支持:
        Bus的消息通讯基础实际上都是由Spring Cloud Stream所提供的。一定程度上，我们可以将Spring Cloud Bus理解为是一个使用了spring cloud stream构建的上层应用。所以当我们要在其他消息代理上使用Spring cloud Bus消息总线时，只需要去实现一套指定消息代理的绑定器即可

消息驱动的微服务：Spring Cloud Stream
    
    构建一个Spring Cloud Stream消费者：
        
    
        
服务链路追踪（Spring cloud Sleuth）:
    
    微服务中的全链路调用的跟踪很重要，通过实现对请求调用的跟踪可以帮助我们快速发现错误根源以及监控分析每条请求链路上的性能瓶颈
    只需要在微服务中导入Spring cloud Sleuth包就能在日志中记录请求链路信息，可读性太差
    
    抽样收集：日志信息收集多少的策略
        默认情况下，Sleuth会使用PercentageBasedSampler实现的抽样策略，已请求百分比的方式配置和收集跟踪信息。我们可以通过在配置文件中
        对其百分比值进行设置，它的默认值为0.1，代表收集10%的请求跟踪信息
        
        ps:在测试的情况下设置为1 spring.sleuth.sampler.percentage=1
    
    与Zipkin整合：
        搭建Zipkin Server:
            创建spring boot 项目，引入zipkin-server、zipkin-autoconfigure-ui 包，并在main 方法类上添加注解@EnableZipkinServer
        为应用引入和配置Zipkin服务：    以实现将跟踪信息输出到Zipkin Server
             1.引入spring-cloud-starter-zipkin jar包
             2.设置抽样收集为100%收集（在测试的情况下）
    
    消息中间键收集：
        实现异步收集
    
    数据存储：
        永久存储
    
    API接口：
    
    
    
    Spring Cloud Sleuth 主要功能就是在分布式系统中提供追踪解决方案，并且兼容支持了 zipkin，你只需要在pom文件中引入相应的依赖即可
 
高可用服务注册中心    
    
    Eureka通过运行多个实例，使其更具有高可用性。事实上，这是它默认的属性，你需要做的就是给对等的实例一个合法的关联serviceurl。
    
    
什么是微服务

    微服务是系统架构上的一种风格，它的主旨是将一个原本独立的系统拆分成多个小型服务，这些小型服务都在各自独立的进程中运行，服务之间基于
    HTTP的RESTful API 进行通信协作。被拆分成的每一个小型服务都围绕这系统中的某一项或一些耦合度较高的业务功能进行构建，
    并且每个服务都维护这自身的数据储存、业务开发、自动化测试案例以及独立部署机制。由于有了轻量级的通信协作基础，所以这些微服务
    可以使用不同的语言编写
    
