# FX-System

### CSDN地址
[CSDN地址](https://blog.csdn.net/qq_37771475)  里面有本人整理的Spring、Spring Boot教程，欢迎讨论交流。

#### 介绍
外汇业务管理系统（FX-System），采用微服务架构设计。功能包含银行卡开户、转账、存款、结算活期利息、外汇兑换、人民币结售汇等。

#### 数据库：MYSQL
ER图
![ER图](https://images.gitee.com/uploads/images/2019/0510/153351_6286278a_2276680.jpeg "ER图.JPG")

#### 软件架构
软件架构说明
![微服务架构图](https://images.gitee.com/uploads/images/2019/0509/172939_eafca58a_2276680.jpeg "系统架构图.JPG")

1. 总架构：采用Spring Cloud作为微服务架构技术。并将系统拆分成fx-server、fx-account、fx-bankcard、fx-auth四个微服务。

    1.1 fx-server微服务
    功能：使用Eureka进行服务治理，以此搭建服务注册中心集群；使用Hystrix Dashboard来实时监控Eureka Client的Hystrix各项指标信息。
    技术：Eureka、Hystrix Dashboard等。

    1.2 fx-account微服务
    功能：基础微服务。负责对FX_ACCOUNT、FX_ROLE、FX_ACCOUNT_ROLE数据表进行CRUD操作。
    所用技术概览：Lombok、Druid、JPA、Spring MVC、Thymeleaf、Feign、Hystrix、Actuator、Eureka-Client等等。

    ![MVC架构图](https://images.gitee.com/uploads/images/2019/0509/173402_fd7e01a3_2276680.png "屏幕截图.png")

    - MVC架构：使用lombok简化POJO操作，使用Druid作为连接池，使用JPA设计数据持久层（DAO），Spring MVC设计控制层（Controlelr），Thymeleaf设计界面层（View）。
    - 服务调用：使用Feign进行声明式服务调用
    - 熔断措施：使用Hystrix作为熔断措施，当调用服务失败时，使用服务降级策略
    - 监控：使用Actuator监控和管理Spring Boot应用，Druid监控SQL
    - 服务注册与发现：使用Eureka-Client来向服务注册中心集群注册服务

    1.3 fx-bankcard微服务
    功能：基础微服务。负责对FX_USER_INFO、FX_BANKCARD、FX_ASSET、FX_TRANSACTION_RECORD、FX_CURRENCY、FX_CURRENCY_PAIRS数据表进行CRUD操作；引入外部API（外汇牌价信息、人民币牌价信息、新闻头条信息、校验身份证）。
    所用技术概览：Actuator、JPA、Spring MVC、Eureka-Client、Hystrix、Feign、Lombok、Druid、FastJson、HttpClient等。

    - MVC架构：使用lombok简化POJO操作，使用Druid作为连接池，使用JPA设计数据持久层（DAO），Spring MVC设计控制层（Controlelr），Thymeleaf设计界面层（View）。
    - 服务调用：使用Feign进行声明式服务调用
    - 熔断措施：使用Hystrix作为熔断措施，当调用服务失败时，使用服务降级策略
    - 监控：使用Actuator监控和管理Spring Boot应用，Druid监控SQL
    - 服务注册与发现：使用Eureka-Client来向服务注册中心集群注册服务
    - API：使用apache的http工具类发送请求获取信息，并使用FastJson对请求到的json字符串解析成对象

    1.4 fx-auth微服务
    功能：外汇业务管理系统的入口，使用Zuul网关来对请求进行分发，使用JWT+Spring Security搭建SSO单点登录来进行认证及授权。
    所用技术概览：Actuator、Redis、Spring Security、Spring MVC、Eureka-Client、Hystrix、Feign、Lombok、JWT、WebSocket（STOMP）、Zuul、FastJson、Thymeleaf、Mail、Jquery、Bootstrap等等

    - 邮件服务：使用spring-boot-starter-mail、异步线程池来构建邮件服务
    - 服务调用：使用Feign进行声明式服务调用（调用fx-account、fx-bankcard的服务）
    - 监控：使用Actuator监控和管理Spring Boot应用
    - 服务注册与发现：使用Eureka-Client来向服务注册中心集群注册服务
    - 前端：采用Thymeleaf作为视图，Jquery、Bootstrap4进行界面渲染
    - SSO单点登录：采用JWT+Spring Security+Redis搭建SSO
    - 外汇牌价数据传输：采用STOMP协议，全双工方式，从服务器定时发送信息至客户端

2. SSO单点登录架构

    2.1 认证流程说明

    Spring Security实际上是通过一组过滤器链来对请求进行拦截操作的，如下图所示：
    
    ![Spring Security过滤器链](https://images.gitee.com/uploads/images/2019/0510/103054_0fa4bbfd_2276680.png "Spring Security过滤器链.png")

    其中UsernamePasswordAuthenticationFilter和BasicAuthenticationFilter是自定义的过滤器。而FilterSecurityInterceptor会对之前自定义的过滤器进行相应判断，并抛出异常（例如身份认证没有通过）。然后由ExceptionTranslationFilter捕获抛出来的异常，进行相应处理。

    2.2 用户登录认证流程
    
    ![用户登录认证流程](https://images.gitee.com/uploads/images/2019/0510/104301_414d2160_2276680.png "用户登录认证流程.png")

    本系统通过setFilterProcessesUrl("/login/auth")设置该过滤器访问地址，然后客户端登录界面先提交数据到后端进行数据校验（Spring Validation），校验无误后再提交登录信息到本过滤器。
    UsernamePasswordAuthenticationFilter接着获取客户的Username和Password将其封装成UsernamePasswordAuthenticationToken对象（Authentication的一个实现类），这个对象中封装了我们需要认证的信息。之后通过调用AuthenticationManager的authenticate方法进行认证。然而实际上认证并不是由AuthenticationManager做的，而是由AuthenticationProvider去实现的，AuthenticationManager的作用是管理一组AuthenticationProvider集合，通过for循环遍历的方式去寻找合适的Provider。这里AuthenticationManager调用DaoAuthenticationProvider去认证。

    ![ DaoAuthenticationProvider的类图](https://images.gitee.com/uploads/images/2019/0510/112829_742d47d7_2276680.jpeg "DaoAuthenticationProvider的类图.jpg")

    DaoAuthenticationProvider通过调用UserDetailsService的loadUserByUsername方法来获取UserDetails对象。然后比对UserDetails的密码与认证请求的密码是否一致，一致则表示认证通过。在认证成功以后会使用加载的UserDetails（包含用户权限等信息）来封装要返回的Authentication对象，并保存在SecurityContextHolder所持有的SecurityContext中，供后续的程序进行调用，如访问权限的鉴定等。然后此Authentication会沿着调用线返回给UsernamePasswordAuthenticationFilter。
    最后就是认证结果的处理部分了。本系统是通过实现UsernamePasswordAuthenticationFilter的successfulAuthentication和unsuccessfulAuthentication方法（也可使用Handler）来对认证结果进行操作。successfulAuthentication方法当认证成功时会调用，这里采用JWT将认证信息封装成Token，存入Redis，并将此Token返回给客户端存入Cookie。而unsuccessfulAuthentication方法是在认证失败时调用，这里将请求forward到Error界面，并对错误结果进行渲染。

3. STOMP over Websocket实现B/S全双工通信

    ![OSI七层模型](https://images.gitee.com/uploads/images/2019/0516/153157_65f4091a_2276680.png "屏幕截图.png")
    ![作用](https://images.gitee.com/uploads/images/2019/0516/153223_7f017d3e_2276680.png "屏幕截图.png")
    
    3.1 Http协议：HTTP协议即超文本传送协议(Hypertext Transfer Protocol )，是一个应用层协议，由请求（request）和响应（response）构成，是一个标准的客户端服务器模型。特点：单工、无状态、一个request一个response。

    3.2 WebSocket协议：WebSocket是一种在单个TCP连接上进行全双工通信的协议。

    3.3 STOMP协议：STOMP即Simple (or Streaming) Text Orientated Messaging Protocol，简单(流)文本定向消息协议，它提供了一个可互操作的连接格式，允许STOMP客户端与任意STOMP消息代理（Broker）进行交互。

    3.4 实现B/S通信的方式
    - Ajax轮询：客户端定时向服务器发送Ajax请求，服务器接到请求后马上返回响应信息并关闭连接。缺点：请求中有大半是无用，浪费带宽和服务器资源。
    - 长轮询（long poll）：客户端向服务器发送Ajax请求，服务器接到请求后hold住连接，直到有新消息才返回响应信息并关闭连接，客户端处理完响应信息后再向服务器发送新的请求。（Http1.1协议中的长连接Persistent Connection）
    注意：Ajax轮询与长轮询都是采用Http协议，而Http协议本身是无状态的，并且是被动的（即服务端不能主动发送消息）

    - WebSocket长连接：WebSocket协议被设计来取代现有的使用HTTP作为传输层的双向通信技术。它通过发送一次Http请求进行握手，然后协议切换成WebSocket，数据就直接从TCP通道进行传输，而与Http无关了。

    3.5 STOMP over WebSocket：STOMP在WebSocket之上提供了一个基于帧的线路格式（frame-based wire format）层，用来定义消息的语义。STOMP帧由命令、一个或多个头信息以及负载所组成。例如，如下就是发送数据的一个STOMP帧：

```
     SEND
     destination:/request/sendUser
     content-length:8
 
     user2,hi
```


4. ELK（ElasticSearch+Logstash+Kibana）实时日志收集分析系统
    
    ![ELK架构图](https://images.gitee.com/uploads/images/2019/0510/151431_c7aca490_2276680.jpeg "ELK架构图.JPG")

    4.1 ElasticSearch是实时全文搜索和分析引擎，提供搜集、分析、存储数据三大功能；是一套开放REST和JAVA API等结构,提供高效搜索功能，可扩展的分布式系统。它构建于Apache Lucene搜索引擎库之上。它的特点有：分布式，零配置，自动发现，索引自动分片，索引副本机制，restful风格接口，多数据源，自动搜索负载等。
    
    4.2 Logstash主要是用来日志的搜集、分析、过滤日志的工具，支持大量的数据获取方式。一般工作方式为c/s架构，client端安装在需要收集日志的主机上，server端负责将收到的各节点日志进行过滤、修改等操作在一并发往ElasticSearch上去。

    4.3 Kibana是一个开源的分析和可视化平台。使用Kibana来搜索，查看，并和存储在ElasticSearch索引中的数据进行交互。可以轻松地执行高级数据分析，并且以各种图标、表格和地图的形式可视化数据。Kibana使得理解大量数据变得很容易。它简单的、基于浏览器的界面使你能够快速创建和共享动态仪表板，实时显示ElasticSearch查询的变化。

    4.4 架构改进：由于Logstash消耗性能，所以高并发场景容易遇到流量上的瓶颈，因此可以搭建Logstash集群。另一方面，可以添加中间件进行日志缓存处理。由于Logstash数据源具有多种方式，所有中间件也可以很多选择，常见的有kafka，redis。

    ![高并发时ELK架构图](https://images.gitee.com/uploads/images/2019/0510/152049_0f55fd71_2276680.png "屏幕截图.png")

### 高可用
- 服务集群：当某个服务结点挂掉时，能确保另一个服务端点可用。
- 服务熔断和降级措施：一个微服务的超时失败可能导致瀑布式连锁反应，进而导致一系列服务不可用。此时采用Hystrix断路器即可防止这种情况的发生。然后，可使用降级服务措施，将请求超时的方法，降级成另一个方法（比如：返回请求超时信息）。
- 监控系统：Druid监控数据库，Actuator监控服务端点，Hystrix-Dashboard作为熔断监控
- 日志系统：采用logback+ELK（ElasticSearch+Logstash+Kibana）搭建实时日志收集分析系统

### 高并发
- 负载均衡：采用轮询算法，从服务集群选择服务端点进行服务调用
- 锁机制：乐观锁（CAS）
- 缓存机制：Redis作为缓存
- 连接池：Druid数据库连接池，Redis缓存连接池，异步线程池

#### 安装教程

1. 数据库安装：从fx-account和fx-bankcard微服务的resources目录下找到schema.sql文件，执行它即可创建数据表。
2. Redis安装：网上关于Redis安装的教程挺多的，就不赘述了。
3. ELK安装：同2。

#### 使用说明

1. 登录界面：localhost:8080/login
2. Druid监控界面：http://localhost:8001/druid/login.html （用户名：admin 密码：123456）
3. Hystrix-Dashboard监控界面：http://localhost:7001/hystrix（http://localhost:8001/actuator/hystrix.stream）
