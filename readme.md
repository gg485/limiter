#一个分布式限流器

使用方法：客户端用法见src/main/java/example/MockClient，很简单。要求先启动ZkServerMain和QuotaServerMain，当然在实际中这两个是在别的机器上启动的。目前zk只支持单点，quotaServer可组成集群，使用一致性哈希进行服务发现。

各包介绍：
1.algorithm 限流算法，默认提供balance（避免饥饿）和mmf（避免超总量）两种算法，也可以自己实现Algo接口。

2.client 具体的限流器客户端实现，提供Allower和Waiter两种，具体区别见注释

3.dao dao。没有提供写库方法。实际生产中一般要写个admin页做服务资源配额管理，懒得写了。所以目前资源配额只能自己导进库里了。以及源码里的用户名和密码为了省事直接明文了。

4.example 例子

5.exceptions 异常类，但写着写着懒得处理异常了。

6.grpc grpc用的stud

7.lane 防止读库并发量过大的工具类

8.model model。

9.server 具体的限流服务器实现

10.zk 操作zk用。