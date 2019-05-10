# FX-System

#### 介绍
外汇业务管理系统（FX-System），采用微服务架构设计。功能包含银行卡开户、转账、存款、结算活期利息、外汇兑换、人民币结售汇等。

#### 数据库：MYSQL
ER图
![ER图](https://images.gitee.com/uploads/images/2019/0509/172751_d4f2c18a_2276680.jpeg "ER图.JPG")

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

    DaoAuthenticationProvider通过调用UserDetailsService的loadUserByUsername方法（本系统是通过Feign调用fx-account的endpoint服务接口来获取账户信息）来获取UserDetails对象。然后把用户信息放在一个已经认证了的Authentication里面。最后Authentication则会沿着调用线最后返回到UsernamePasswordAuthenticationFilter。
    本系统实现了UsernamePasswordAuthenticationFilter的successfulAuthentication和unsuccessfulAuthentication方法（也可使用Handler）来对认证结果进行操作。successfulAuthentication方法当认证成功时会调用，这里采用JWT将认证信息封装成Token，存入Redis，并将此Token返回给客户端存入Cookie。而unsuccessfulAuthentication方法是在认证失败时调用，这里将请求forward到Error界面，并对错误结果进行渲染。

    2.3 用户访问服务流程（Basic）

#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)