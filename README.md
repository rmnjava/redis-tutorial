## 使用redis来构建简单应用的实例

### 目的

1. 测试redis事务的用法;
2. 测试通过Sentinel实现redis节点容灾;

### 注意事项

1. 在执行 Sentinel 时必须显示的指定一个配置文件,并且Sentinel进程会复写该文件,所以在每次启动Sentinel时都必须还原该文件,否则可能引起程序结果异常;
2. 在实际的环境中,最小的Sentinel集群为三个节点:一个节点在Sentinel崩溃的情况下将无法进行容灾;二个节点的情况下如果一个Sentinel崩溃,那么由于故障转移需要得到集群中超过二分之一的机器的授权,一台机器将同样无法进行故障转移.具体请参考 [redis sentinel documentation](https://redis.io/topics/sentinel);

### 测试环境

```bash
cat /proc/version
#Linux version 4.4.0-67-generic (buildd@lgw01-46) (gcc version 5.4.0 20160609 (Ubuntu 5.4.0-6ubuntu1~16.04.4) ) #88-Ubuntu SMP Wed Mar 8 16:34:45 UTC 2017

java -version
#openjdk version "1.8.0_121"
#OpenJDK Runtime Environment (build 1.8.0_121-8u121-b13-0ubuntu1.16.04.2-b13)
#OpenJDK 64-Bit Server VM (build 25.121-b13, mixed mode)

redis-server --version
#Redis server v=3.0.6 sha=00000000:0 malloc=jemalloc-3.6.0 bits=64 build=687a2a319020fa42
```


### 使用方法:

1. 通过 git clone 复制到本地;
2. `mvn clean compile` 编译文件;
3. `sh start.sh` 启动两个redis实例和一个Sentinel;
4. 使用 `mvn spring-boot:run` 或者直接在IDE中运行 `Application.class` 文件,建议直接运行`Application.class`,使用mvn运行不不能打断点;
5. 使用 POSTMAN 通过 post 方法调用 `http://127.0.0.1:8180/user/` 接口(注意,因为本人的测试机上8080端口分配给了zookeeper,所以没有使用默认端口);
6. 在第一次执行 `userService.register()` 方法,可以成功注册,并返回;第二次注册将返回-1;


### 事务功能的测试

1. `FLUSHDB` 清空Redis确保当前数据库中没有我们要注册的键;
2. 在 `userService.register()`方法的 `boolean isMember` 方法处打断点;
3. 使用POSTMAN通过post方法调用 `register` 方法,执行到步骤2的断点处,下一步,此时判断 isMember 应该为 false;
4. 使用 `redis-cli` 随意向 KeyGenerator.USER_ID 或 KeyGenerator.USER_NAME 插入任意一条数据;
5. 继续执行断点后的内容,在 `transaction.exec()` 处会发现返回的list长度为0;

具体原理请参照: [redis事务](https://redis.io/topics/transactions)


### redis节点容灾测试

1. 成功启动应用后, `netstat -anp | grep 6378` 找到master的pid, `kill -9 ${pid}` 杀死master;
2. 通过POSTMAN请求调用register()方法,查看是否能自动的将 6380 转为master节点;
