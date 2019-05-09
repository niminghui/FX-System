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
    所用技术概览：Lombok、Druid、JPA、Spring MVC、Thymeleaf、Feign、Hystrix、Actuator等等。
    - MVC架构：使用lombok简化POJO操作，使用Druid作为连接池，使用JPA（Hibernate实现）设计数据持久层（DAO），Spring MVC设计控制层（Controlelr），Thymeleaf设计界面层（View）。
     ![MVC架构图](https://images.gitee.com/uploads/images/2019/0509/173402_fd7e01a3_2276680.png "屏幕截图.png")
    - 服务调用：使用Feign进行声明式服务调用
    - 熔断措施：使用Hystrix作为熔断措施，当调用服务失败时，使用服务降级策略
    - 监控：使用Actuator监控和管理Spring Boot应用

  1.3 fx-bankcard微服务
    功能：
    

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