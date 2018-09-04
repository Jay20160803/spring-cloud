什么是微服务:

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

        服务组件化、
        按业务组织团队、
        做产品的态度
        智能端点与哑管道：
            智能端点：
            哑管道：非智能，不具备智能路由等高级功能的消息系统    
            在微服务架构中，通常会使用一下两种服务调用方式：
                一：使用HTTP的RESTful API或轻量级的消息发送协议，实现信息传递与服务调用的触发
                二：通过在轻量级消息总线上传递消息，类似RabbitMQ等一些提供可靠异步交换的中间件
                
        去中心化治理      
        去中心化处理数据
        基础设施自动化：    自动化测试、自动化部署
        容错设计
        演进设计
     
微服务通讯原则：

    微服务的通讯类型：
    微服务之间有两种主要的通信形式
        1.请求-响应：一个服务通过发出指定请求来调用另一个服务，这种方式通常用于存储或检索数据。该服务然后等待响应：得到某个响应资源或响应确认
        2.观察者：基于事件的隐式调用，其中一个服务发布事件，其他一个或多个正在监视该事件的观察者通过异步运行逻辑来响应该事件
        了解何时何地使用请求-响应模型与观察者模型是设计有效的微服务通讯的关键
        
    请求-响应：
        为服务之间的请求-响应通讯用于一个服务发送请求并期望放回资源或确认响应
        实现这种模式的最基本的方法就是HTTP，最好遵循REST原则。两个微服务之间的标准HTTP通信管道通常如下所示：
        原始服务--->Http负载平衡器--->后端服务
        在这种方法中，有一个简单的负载均衡器可以位于服务通信中间，原始服务可以向负载均衡器发出HTTP请求，负载平衡器可以将该请求转发到后端微服务的其中任何一个实例。
        但是，在某种情况下，服务之间的流量非常高，或者开发团队希望尽可能减少微服务之间的延迟。在这种情况下，他们可能采用胖客户端负载平衡。这种方法通常使用诸如Consul
        ，Eureka或Zookeeper之类的系统来跟踪微服务实例及其IP地址的集合。然后，发起的微服务可以直接向需要与之通讯的后台服务实例发出请求
        
    观察者模型：
        观察者模型对于扩展微服务至关重要。并不是每个通讯都需要回应或确认。事实上，在许多长时间的工作流程中，至少有一些逻辑实现应该是完全异步和非阻塞的。
        分发这种类型的工作负载的标准方式是使用代理服务来传递消息，理想的是实现一个队列。RabbitMQ、ZeroMQ、Kafka甚至Redis Pub/Sub都是可以作哑管道，它们允许微服务
        发布事件，同时允许其他微服务订阅它们感兴趣的事件。
        
        这种方法的巨大优势在于，发布服务方不需要知道事件订阅了多少用户，或者这些订阅者做什么事情来响应自己的事件。在消息者发生故障的情况下，大多数队列系统具有重试/重新
        发送功能以确保消息最终被消费。生成者只需要“发送然后忘记”即可，相信消息代理的队列将确保消息最终到达正确的消费者。即使所有消费者都忙于无法立即回应事件，队列仍然会
        持久保存，直到消费者准备好处理事件
        
        观察者模型的另一个好处是微服务系统的未来可扩展性。一旦在生成者服务中实现了事件广播，新的消费者类型可以在事件发生之后被添加和订阅，而不需要改变生成者。
    
        观察者模型是微服务部署中非常强大的工具，不实现观察者通讯的微服务就无法发挥其全部潜力
        
        
服务治理：

    eureka-service: 服务注册中心
    eureka-clinet:任何服务都可以作为eureka-clinet,并注册到eureka-service中
    
    服务治理可以说是微服务中最为核心和基础的模块，它主要要来实现各个微服务实例的自动化注册与发现
    
    服务注册：
        在微服务治理框架中，通常都会构建一个注册中心，每个服务单元向注册中心登记自己提供的服务，将主机与端口号、版本、通讯协议等一些附加信息告知注册中心，注册中心按服务名
        分类组织服务清单，服务注册中心还需要以心跳的方式去监测清单中的服务是否可用，若不可用需要从服务中剔除，达到排除故障的效果。
        
    服务发现：
        由于在服务治理框架下运作，服务间的调用不再通过指定具体的实例地址来实现，而是通过向服务名发起请求调用实现。所以，服务调用方在调用服务提供方接口的时候，并不知道具体的
        服务实例位置。因此，调用方需要向服务注册中心咨询服务，并获取所有服务的实例清单，以实现对具体服务实例的访问
        
    Netfilx Eureka:
        Spring Cloud Eureka,使用Netfix Eureka来实现服务注册与发现，它既包含了服务端组件，也包含了客户端组件
        Eureka服务端，我们称它为服务注册中心。它同其他的服务注册中心一样，支持高可用配置
        Eureka客户端，主要处理服务的注册与发现。客户端服务通过注解和参数配置的方式，嵌入在客户端应用程序的代码中，在应用程序运行时，Eureka客户端向注册中心注册自身提供的服务
                     并周期性的发送心跳来更新它的服务租约。同时，它也能从服务端查询当前注册服务信息并把它们缓存到本地并周期性的刷新服务状态
                     
    搭建服务注册中心：
        1.添加spring-cloud-starter-eureka-server的jar包
        2.在spring-boot的启动类上添加@EnableEurekaServer注解
        3.在application.yaml 添加配置信息，配置信息会在之后详解
        
        注意：eureka-server 在配置时需要配置eureka.client.serviceUrl.defaultZone 如果不添加会一直处于保护状态
        在默认的情况下，该服务注册中心会将自己作为客户端尝试来注册自己，所以我们需要禁用它的客户端注册行为
    
    注册服务提供者（Eureka客户端）：
        1.添加spring-cloud-starter-eureka
        2.在spring-boot启动类上添加@EnableDiscoveryClient或者@EnableEurekaClient,两者功能一样，前者支持其他的服务治理组件（Zookeeper等）
        3.在application.yaml 添加配置信息 ，配置信息在之后详解
        
        注意：spring cloud的jar包需要自动管理版本是需要添加
        dependencyManagement {
        	imports {
        		mavenBom 'org.springframework.cloud:spring-cloud-dependencies:Edgware.SR2'
        	}
        }
        
     高可用注册中心
        在微服务架构这样的分布式环境中，我们需要充分考虑发生故障的情况，所以在生产环境中必须对各个组件进行高可用部署，对于微服务如此，对于服务注册中心也一样。
        Eureka Server的设计一开始就考虑了高可用问题，在Eureka的服务治理设计中，所有节点即是服务提供方，也是服务消费方，服务注册中心也不例外。
        Eureka Server的高可用实际上就是将自己作为服务向其他服务注册中心注册自己，这样就可以形成一组互相注册的服务注册中心，以实现服务清单的互相同步，达到高可用的效果。
        
        Eureka Server高可用：
            1.配置文件的eureka.client.serviceUrl.defaultZone属性指向另一台Eureka server
            2.eureka.instance.hostname 指定的域名要能访问到，可以在浏览器中测试
            3.eureka client配置文件中的eureka.client.serviceUrl.defaultZone属性指向多台Eureka Server用逗号隔开
            
     Eureka Server自我保护机制：
        默认的情况下，如果Eureka Server在一定时间内（默认90秒）没有接收到某个微服务实例的心跳，Eureka Server将会移除该实例。但是当网络分区故障（可以理解为因为网络原因造成的通信故障）
        发生时，微服务与Eureka server之间无法正常通讯，而微服务本身是正常运行的，此时不应该移除这个服务，所以引入了自我保护
        
        自我保护机制：
            自我保护模式正是一种针对网络异常波动的安全保护措施，使用自我保护模式能使Eureka集群更加的健壮、稳定的运行。
            自我保护机制的工作机制是如果在**15分钟**内超过**85%**的客户端节点都没有正常的心跳，那么Eureka就可以认为客户端与注册中心出现了网络故障，Eureka Server自动进入自我保护机制
            
            自我保护机制下的几种情况：
                1.Eureka Server不再从注册列表中移除因为长时间没有收到心跳而应该过期的服务
                2.Eureka Server仍然能够接收新服务的注册和查询请求，但是不会被同步到其它节点上，保证当前节点依然可用
                3.当网络稳定时，当前Eureka Server新的注册信息会被同步到其它节点中
                
            自我保护开关：eureka.server.enable-self-preservation  默认打开状态，建议生产环境打开此配置
            
            开发环境配置：
                开发环境中如果要实现服务失效能自动移除，只需要修改以下配置
                    1.注册中心关闭自我保护机制，修改检查失效服务的时间
                        server端:
                        eureka.server.enable-self-preservation//（设为false，关闭自我保护主要）
                        eureka.server.eviction-interval-timer-in-ms//清理间隔（单位毫秒，默认是60*1000）
                        
                    2.微服务修改减短服务心跳的时间 
                        client端：
                        eureka.client.healthcheck.enabled = true//开启健康检查（需要spring-boot-starter-actuator依赖）
                        eureka.instance.lease-renewal-interval-in-seconds =10//租期更新时间间隔（默认30秒）
                        eureka.instance.lease-expiration-duration-in-seconds =30//租期到期时间（默认90秒
                        
              **以上配置建议在生产环境使用默认的时间配置。**
         
     服务发现与消费
     
         服务消费者，它主要完成两个目标，发现服务以及消费服务。其中服务发现的任务由Eureka的客户端完成，而服务消费的任务由Ribbon完成。Ribbon是一个基于HTTP和TCP的客户端负载均衡器，它
         可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到负载均衡的作用。当Ribbon与Eureka联合使用时，Ribbon的服务实例清单RibbonServerList会被DiscoveryEnabledNIWSServerList
         重写，扩展成从Eureka注册中心中获取服务端列表。同时它也会用NIWSDiscoveryPing来取代IPing，它的职责委托给Eureka来确定服务端是否已经启动
         
         客户端负载均衡与服务端负载均衡：
            服务端负载均衡：
                负载均衡是我们处理高并发、缓解网络压力和进行服务端扩容的重要手段之一，服务端负载均衡分为2种，一种是硬件负载均衡，还一种是软件负载均衡。
                硬件负载均衡主要通过在服务器节点之间安装专门用于负载均衡的设备，常见的如F5.
                软件负载均衡主要是在服务器上安装一些具有负载均衡功能的软件来完成请求分发进而实现负载均衡，常见的就是Nginx
                
                无论是硬件负载均衡还是软件负载均衡，它的工作原理都不外乎以下流程：
                用户----->负载均衡服务器---->服务中的某个节点
                
                无论是硬件负载均衡还是软件负载均衡都会维护一个可用的服务端清单，然后通过心跳机制来删除故障的服务节点以保证清单中都是可以正常访问的服务端节点，此时当客户端的请求到达负载均衡服务器时，
                负载均衡服务器按照某种配置好的规则从可用服务清单中选出一台服务器去处理客户端的请求。这就是服务端负载均衡
            
            客户端负载均衡：
                客户端负载均衡和服务端负载均衡最大的区别在于服务清单所存储的位置。在客户端负载均衡中，所有的客户端节点都有一份自己要访问的服务清单，这些清单统统都是从Eureka服务注册中心获取的。
   
         创建服务消费者：
            注意：在Spring Cloud中没有任何服务可同时即是服务消费者，又是服务提供者
            1.添加spring-cloud-starter-ribbon、spring-cloud-starter-eureka jar包
            2.配置RestTemplate bean,并在bean方法上添加@LoadBalanced 开启负载均衡
            3.在配置文件中添加eureka.client.service-url.defaultZone
            4.使用服务名调用服务
            ribbon的细节在之后介绍
                @RequestMapping("consumer")
                public String consumer(){
            
                    return restTemplate.getForObject("http://ribbon-consumer/provider",java.lang.String.class);
                }
                
     Eureka 详解：
         
         基础架构：
                服务注册中心：Eureka提供的服务端，提供服务注册与发现的功能
                服务提供者：将自己提供的服务注册到Eureka
                服务消费者：消费者从服务注册中心获取服务列表，从而使消费者可以知道去何处调用其所需要的服务
                很多时候，客户端既是服务提供者也是服务消费者
         
         服务治理机制：
                1.注册：客户端在启动的时候会通过发送REST请求的方式将自己注册到Eureka Server上，同时带上自身服务的一些元数据信息
                2.续约：
                    客户端注册服务之后，服务提供者会维护一个心跳来持续告诉Eureka Server “我还活着”，以防止Eureka Server的“剔除任务”将该服务实例从服务列表中排除出去
                    eureka.instance.lease-renewal-interval-in-seconds参数用于定义服务续约任务的调用间隔时间，默认为30秒
                    eureka.instance.lease-expiration-duration-in-seconds参数用于定义服务失效的时间，默认为90秒
                    
                3.下线：
                    当服务实例进行正常的关闭操作时，它会触发一个服务下线的REST请求给Eureka Server，告诉服务注册中心“我要下线了”
                    在Idea中停止程序不会调用 ,可以在命令行中启动和停止验证
                    
                4.获取服务列表：
                    启动客户端的时候，会从注册中心获取注册的服务清单，出于性能考虑，Eureka Server会维护一份只读的服务清单来返回给客户端，同时该缓存清单会每隔30秒更新一次
                    获取服务是客户端的基础，所以必须确保eureka.client.fetch-registry=true.若希望修改缓存清单的更新时间，可以通过修改eureka.client.registry-fetch-interval-seconds
                    参数进行修改，默认值为30，单位为秒
                    
                5.服务同步：客户端发送注册请求到一个服务注册中心时，它会将该请求转发给集群中相连的其他注册中心，从而实现注册中心之间的服务同步
         
                6.失效剔除：
                    有些时候，我们的服务实例并不一定会正常下线，可能由于内存溢出、网络故障等原因使得服务不能正常工作，而服务注册中心并未收到“服务下线请求”。
                    为了从服务列表中将这些无法提供服务的实例剔除，Eureka Server在启动的时候会创建一个定时任务，默认每隔一段时间（默认60秒）将当前清单中超时（默认为90秒）没
                    有续约的服务剔除出去
                7.自我保护
                    当我们在本地调试基于Eureka的程序时，基本上会碰到这个问题。Eureka Server在运行期间，会统计心跳失败的比例在15分钟之内是否低于85%，如果出现低于的情况（在单机调试的时候很容易满足，
                    实际生产环境上通常是由于网络不稳定导致），Eureka Server会将当前的实例注册信息保护起来
    配置详解：
        
         在Eureka的服务治理体系中，主要分为服务端与客户端两个角色，服务端为服务注册中心，而客户端为各个提供接口的微服务应用。在高可用的注册中心中，服务注册中心也是客户端
         Eureka服务端更多地类似于一个现成产品，大多数情况下，我们不需要修改它的配置信息。可以查看org.springframework.cloud.netflix.eureka.server.EurekaServerConfigBean
                  类的定义
                  
         Eureka客户端的配置主要分为以下两个方面：
         
                  1.服务注册相关的配置信息，包括服务注册中心的地址、服务获取的间隔时间、可用区域等
                  2.服务实例相关的配置信息，包括服务实例的名称、IP地址、端口号、健康检查路径等
                 
                  
                  服务注册类配置：
                  
                    eureka.client为前缀 可以查看org.springframework.cloud.netflix.eureka.EurekaClientConfigBean
                    
                    
                    serviceUrl.defauktZone:  指定注册中心  在构建高可用的服务注册中心集群时，我们可以配置多个注册中心的地址（通过逗号分隔）
                    registryFetchIntervalSeconds：   从Eureka服务端获取注册信息的间隔时间，单位为秒  默认30
                    
                    
                  服务实例类配置：
                     eureka.instance为前缀 可以查看org.springframework.cloud.netflix.eureka.server.EurekaInstanceConfigBean
                     
                     端点配置：
                        eureka.instance.statusPageUrlPath    默认使用Spring-boot-actuator模块提供的/info端点，当/info端点不正确的情况下，会导致在Eureka面板中单击服务实例时，无法访问到服务实例提供的信息接口
                        eureka.instance.healthCheckUrlPath    默认使用Spring-boot-actuator模块提供的/health端点
                        
                     健康检测：
                        默认情况下，Eureka中各个服务实例的健康检测并不是通过spring-boot-actuator模块的/health端点来实现的,而是依靠客户端心跳的方式来保持服务实例的存活。
                        在Eureka的服务续约与剔除机制下，客户端的健康状态从注册到注册中心开始都会处于UP状态，除非心跳终止一段时间之后，服务注册中心将其剔除。默认的心跳实现方式可以
                        有效检查客户端进程是否正常运作，但无法保证客户端应用能够正常提供服务。
                        
                        在Spring Cloud Eureka中，我们可以通过简单的配置，吧Eureka客户端的健康检测交给spring-boot-actuator模块的/health端点，以实现更加全面的健康状态维护
                        1.添加spring-boot-starter-actuator模块的依赖
                        2.配置中添加eureka.client.healthcheck.enabled=true
                        
                        这个还需要去了解spring boot中 actuator相关的设置
                        
                     其他配置：
                        preferIpAddress: 是否优先使用IP地址作为主机名的标识  默认false
                        leaseRenewallntervallnSeconds:  Eureka客户端向服务端发送心跳的时间间隔， 单位为秒  默认30
                        leaseExpirationDurationlnSeconds： Eureka服务端在收到最后一次心跳之后等待的时间上限，  默认 90
                                                          单位为秒。超过该时间之后服务端会将该服务实例从服务
                                                          消单中剔除， 从而禁止服务调用请求被发送到该实例上
                            
客户端负载均衡（Spring Cloud Ribbon）：

    Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，可以轻松地将面向服务的REST模板请求，自动转换成客户端负载均衡的服务调用
    虽然只是一个工具类框架不需要独立部署，但是它几乎存在于每一个Sprig Cloud构建的微服务和基础设施中。因为微服务间的调用，API网关的请求转发等内容，实际上都是通过Ribbon实现的，
    包括后续我们将要介绍的Feign，它是基于Ribbon的实现工具


    在微服务中使用Spring Cloud Ribbon： 之前有介绍
       
        
    RestTemplate详解：
        
        GET/PUT/DELETE/POST 4种请求的多个接口，具体看代码
        
    参数配置：
        1.全局配置：ribbon.<key>=value
        2.指定客户端配置：<client>.ribbon.<key>=<value>
        3.所有的参数配置请参考DefaultClientConfigImpl类中的默认值，当没有配置对应的参数的时候，使用该类中的默认值，可断点查看参数的值
        
        常见的配置：
            # Max number of retries on the same server (excluding the first try)
            sample-client.ribbon.MaxAutoRetries=1
            
            # Max number of next servers to retry (excluding the first server)
            sample-client.ribbon.MaxAutoRetriesNextServer=1
            
            # Whether all operations can be retried for this client
            sample-client.ribbon.OkToRetryOnAllOperations=true
            
            # Interval to refresh the server list from the source
            sample-client.ribbon.ServerListRefreshInterval=2000
            
            # Connect timeout used by Apache HttpClient
            sample-client.ribbon.ConnectTimeout=3000
            
            # Read timeout used by Apache HttpClient
            sample-client.ribbon.ReadTimeout=3000
            
            
     RIBBON：重试机制       
            
           由于Spring Cloud Eureka实现的服务治理机制强调了CAP原理中的AP，即可用性与可靠性，Eureka为了实现更高的服务可用性，牺牲了一定的一致性，在
           极端情况下它宁愿接受故障的实例也不丢掉“健康的实例”，比如自我保护机制。
           
           由于Spring Cloud Eureka在可用性与一致性上的取舍，不论是由于触发了保护机制还是服务剔除的延迟，引起服务调用到故障实例的时候，我们希望能够增强
           对这类问题的容错。所以我们在实现服务调用的时候通常会加入一些重试机制
           
           步骤：
            1：添加配置开启重试
                ribbon.OkToRetryOnAllOperations=true   默认设置是false
            2.添加spring-retry 依赖
            3.其他参数，可根据需求修改
               
               
    
服务容错保护（Spring Cloud Hystrix）：

    1.拥有断路器逻辑,当断路器打开的时候，会直接返回服务降级，不会因为依赖服务的问题造成调用方的请求阻塞，影响调用方的服务
    2.拥有服务降级，在请求依赖超时，报错，断路器打开时，直接返回服务降级的逻辑
    3.线程和信号隔离、不同的服务依赖使用不同的线程池
    4.请求合并，在指定的时间段内对同一个url的请求可以合并，通过设置和添加批量查询的接口
    5.服务监控，统计服务依赖调用结果
    
    使用Hystrix：
        1.添加spring-cloud-starter-hystrix依赖
        2.入口类添加@EnableCircuitBreaker
        3.在请求方法上添加@HystrixCommand注解
        
        
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
        
        通过设置命令组,Hystrix会根据组来组织和统计命令的告警、仪表盘等信息，Hystrix命令默认的线程划分也是根据命令分组来实现的。默认情况下，Hystrix会让相同组名的命令使用同一个线程池
       
        
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

     Ribbon 和Hystrix几乎要同时使用：
        Ribbon的缺点：
            在实际的开发中，由于对微服务依赖的调用不止一处，往往一个接口会被多处调用，所以我们通常都会针对各个微服务自行封装一些客户端类来包装这些依赖服务的调用。
                这个时候我们会发现，由于RestTemplate的封装，几乎每一个调用都是简单的模板化内容
        Hystrix要在没个请求的方法上添加@HystrixCommand和对应的属性设置，样板代码也很多

                        
     基于Netfix实现，整合了Spring Cloud Ribbon与Spring Cloud Hystrix,除了提供了两者的功能之外，它还提供了一种申明式的Web服务客户端定义方式
     
     参数绑定：
        支持Spring MVC 常用的参数绑定方式，具体看service-feign代码,这里需要注意，在定义各个参数绑定时，@RequestParam、@RequestHeader、@RequestBody、@RequestPart
        等可以指定参数名称的注解，它们的value不能少。在Spring MVC程序中，这些注解会根据参数名作为默认值，但是在Feign中绑定参数必须通过value属性来指明具体的参数名，不然会抛出IllegalStateException
     
     文件上传：
        

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
                     
                     
                    
   
    
  
    
分布配置中心（Spring cloud config）

    Spring Cloud Config 是Spring Cloud团队创建的一个全新项目，用来为分布式系统中的基础设施和微服务应用提供集中化的外部配置支持，它分为服务端
    与客户端两个部分。其中服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置仓库并为客户端提供获取配置信息、加密/解密信息等访问接口；
    而客户端则是为服务架构中的各个微服务应用或基础设施，它们通过指定的配置中心来管理应用资源与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息
    
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

    注意在配置config客户端的时候，config的配置信息必须写在bootstrap命名的配置文件中，因为它的属性加载顺序高于外部配置文件
             
消息总线（Spring Cloud Bus）：
    
    在微服务架构的系统中，我们通常会使用轻量级的消息代理来构建一个共用的消息主题让系统中所有微服务实例都链接上来，由于该主题中产生的消息会被所有实例监听和消费，所以我们称它为消息总线。
    在总线上的各个实例都可以方便地广播一些需要让其他连接在改主题上的实例都知道的消息，例如配置信息的变更或者其他一些管理操作等。
    
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
