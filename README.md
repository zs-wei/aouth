# oauth-master
OAuth 2.0 认证平台 Java 


整体架构思想 分析
                                           JSON传递                   提供核心业务
  http调用API1 nginx负载  OAuth   授权机制       ->OpenAPI接口服务1(dubbo consumer)  ->   负载服务1(dubbo provider)   ->  DB 集群1  Mongo、MySQL   等 （读写分离、水平扩展等）（平台库）
  http调用API2 nginx负载  OAuth   授权机制       ->OpenAPI接口服务1(dubbo consumer)  ->   负载服务2(dubbo provider)   ->  DB 集群2  Mongo、MySQL   等   (读写分离、水平扩展等）（平台库）
  http调用API3 nginx负载  OAuth   授权机制       ->OpenAPI接口服务1(dubbo consumer)  ->   负载服务3(dubbo provider)   ->  DB 集群3  Mongo、MySQL   等    (读写分离、水平扩展等）（平台库）
  
  
  业务子库
  
  
  
                                                                                  Zookeeper（管理整个 dubbo服务实现 与注册  ）
                                                                                                                                                                                                                                 消息总线的建设 （解决具体需要消息队列的任务，用户同步服务等）     Kafka
                                                                                   -》  各种任务                                                                                                                                             
                                                                                                                                                                                                                                  数据同步服务等服务 （）    
                                                                                   -》  各种数据整合 
                                                                                                                                                                                                                                   大数据分析-> Hadoop 解决技术框架  各种数据挖掘算法  人工智能算法
                                                                                  -》 数据研究等支撑、决策支撑等           
                                                                                  -》数据库设计的质量，决定一切的根本 (设计好很难，面向对象设计)
                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                                                                                                                                            
  分布式服务调用的事务处理                                                                                                                                                                                                                                                                                                                                                                                          
 ES 数据搜索服务     
   数据同步服务       
   软负载   提供超强的稳定性
  各单体应用  ->  权限（应用中心管理） ->通过暴露API获取核心数据 
 各应用端看需求结合是否集成单点登录
 单体应用 shiro JWT（APP端认证（token）） cas等开源 spring 
  
  思想技术切换到springcloud（微服务）
  
 数据库 关系与非关系性数据的优势都需要考虑，不能屁股决定脑袋
 并发：负载 + 缓存（redis等） 
 netty 网络通信服务 TCP/IP协议
 
 

 技术积少成多，技术为具体的业务服务，框架也是为具体的业务服务
 
 平台考虑技术-》稳定、可行性方案
 严格的版本控制与管理

优化-》
前端 （）
业务实现（各种Java细节优化、各种设计模式的选用-必须时间的积累与强大的基础知识）
数据库
容器  （TCP）
缓存（统一模块调用比较好）
框架细节优化 (连接池、DAO选择)







服务设计实现全部 采用领域模型 放弃常规模式







    
    
                                                                                                                                                                                                                                    
  
  
  
  
  
  
  
  
  
  
  