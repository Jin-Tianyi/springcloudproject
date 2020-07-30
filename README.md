##### 负载均衡
负载均衡（Load Balance）其意思就是分摊到多个操作单元上进行执行，例如Web服务器、FTP服务器、企业关键应用服务器和其它关键任务服务器等，从而共同完成工作任务，简单地说就是将用户的请求平摊的分配到多个服务上，从而达到HA(高可用)；常见的负载均衡有软件Nginx、LVS,硬件F5等；

##### Ribbon
Ribbon是Netflix发布的云中间层服务开源项目，其主要功能是提供客户端实现负载均衡算法。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，Ribbon是一个客户端负载均衡器，我们可以在配置文件中Load Balancer后面的所有机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器，我们也很容易使用Ribbon实现自定义的负载均衡算法。

##### Ribbon客户端负载均衡 VS Nginx服务端负载均衡
&#160; &#160; &#160; &#160;Nginx是服务器端负载均衡（集中式LB），客户端所有请求都会交给Nginx,然后有Nginx实现转发请求。如客户端C1请求服务器S1,S1通过Nginx负载均衡转发请求至S2、S3，C1只知道请求的服务器为S1，但实际请求的是S2、S3,即负载均衡由服务端实现，客户端不知道具体细节。
&#160; &#160; &#160; &#160;Ribbon为客户端负载均衡(进程内LB)，服务消费者在调用服务提供者（集群）微服务接口时，会在注册中心上获取服务提供者注册信息服务列表之后缓存到JVM本地，通过某种负载均衡算法（如轮询）选择调用的服务的地址，从而在本地实现RPC远程调用技术。

##### Ribbon简单使用

Ribbon负载均衡：Ribbon+RestTemplate远程调用。

<h5>Eureka Client 服务消费者客户端<h5>

`pom.xml`
```
...
<!-- ribbon-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
...
```
<p>spring-cloud-starter-netflix-eureka-client依赖中已经整合spring-cloud-starter-netflix-ribbon,pom中可不重新引入。</p>

![](images/1911127-20200731002949925-1009605587.png)

`RestTemplateConfig.java`
```
/**
 * @author :jty
 * @date :20-7-28
 * @description :注入RestTemplate Bean
 */
@Configuration
public class RestTemplateConfig {
    /**
     * @LoadBalanced 通过服务名调用，开启Ribbon负载均衡,默认轮询
     * */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

`AdminController.java`
```
/**
 * @author :jty
 * @date :20-7-28
 * @description : 管理员模块
 */
@RestController
public class AdminController {
    @Autowired
    RestTemplate restTemplate;
    /**通过服务名调用*/
    private static final String USER_MODULE_URL = "http://user-server";
    /** RestTemplate forObject */
    @GetMapping(value = "/admin/get/userObject/{userId}", produces = "application/json;charset=utf-8")
    public Result searchUser(@PathVariable int userId) {
        Result result = restTemplate.getForObject(USER_MODULE_URL + "/get/user/" + userId, Result.class);
        return result;
    }
    /** RestTemplate forEntity */
    @GetMapping(value = "/admin/get/userEntity/{userId}", produces = "application/json;charset=utf-8")
    public Result findUser(@PathVariable int userId) {
        ResponseEntity<Result> entity = restTemplate.getForEntity(USER_MODULE_URL + "/get/user/" + userId, Result.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new Result(-200,"请求失败！");
        }
    }
    @GetMapping(value = "/admin/post/create/user", produces = "application/json;charset=utf-8")
    public Result createUser(@RequestBody User user) {
        Result result = restTemplate.postForObject(USER_MODULE_URL + "/post/create/user", user, Result.class);
        return result;
    }
}
```
`UserController.java`
```
/**
 * @author :jty
 * @date :20-7-31
 * @description :用户模块
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${server.port}")
    String serverPort;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 查询用户
     */
    @GetMapping(value = "/get/user/{userId}", produces = "application/json;charset=utf-8")
    public Result searchUser(@PathVariable(value = "userId") int userId) {
        User user = userMapper.getUserById(userId);
        if (user != null) {
            return new Result(200, "成功" + serverPort, user);
        }
        return new Result(-200, "无数据");
    }

    /**
     * 添加用户
     */
    @PostMapping(value = "/post/create/user", produces = "application/json;charset=utf-8")
    public Result createUser(@RequestBody User user) {
        int i = userMapper.createUser(user);
        if (i > 0) {
            return new Result(200, "成功" + serverPort, i);
        }
        return new Result(-200, "插入失败");
    }

    /**
     * 服务发现
     */
    @GetMapping(value = "/get/user/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        List<ServiceInstance> instances = discoveryClient.getInstances("user-server");
        for (String sv : services) {
            logger.info("------------>service{}", sv);
        }
        for (ServiceInstance instance : instances) {
            logger.info("-->{}---{}---{}---{}<--", instance.getInstanceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }
        return instances;
    }
}
```
<h6>启动服务</h6>

![](images/1911127-20200731003906087-1137842519.png)

<h6>测试</h6>

<p>8002</p>

![](images/1911127-20200731005450788-556831058.png)


<p>8003</p>

![](images/1911127-20200731005436874-479450434.png)

<h5>Ribbon 现成的几种负载均衡策略</h5>

```
#位置 com.netflix.loadbalancer包下
#轮询策略，遍历所有
RoundRobinRule 
#随机，从可用服务列表中随机拉取服务节点Server
RandomRule   
#重试策略，首先使用轮询策略进行负载均衡，如果轮询失败，则再使用轮询策略进行一次重试，相当于重试下一个节点，看下一个节点是否可用，如果再失败，则直接返回失败。
RetryRule  
#最低并发策略，选择一个并发量最小的server返回，即ServerStats.activeRequestCount最小值
BestAvailableRule 
#区域权衡策略，复合判断server所在区域的性能和server的可用性，来选择调用server
ZoneAvoidanceRule 
#响应时间加权策略，根据响应时间，分配一个权重weight，响应时间越长，weight越小，被选中的可能性越低。
WeightedResponseTimeRule  
#可用过滤策略，过滤掉连接失败的服务节点，并且过滤掉高并发的服务节点，然后从健康的服务节点中，使用轮询策略选出一个节点返回
AvailabilityFilteringRule 
```

<h5>替换Ribbon负载均衡策略</h5>

&#160; &#160; &#160; &#160;SpringCloud Eureka默认负载均衡策略为轮询，我们可根据需要替换策略；替换方式如下：
&#160; &#160; &#160; &#160;自定义配置类注入该策略Bean，为主启动类添加@RibbonClent注解（注：单对某个微服务定制负载均衡策略时，该自定义配置类不能在主启动类所在包及子包下，即不能放在@ComponentScan所扫描包及子包下，需新建一个包；若对所有微服务替换负载均衡策略则需放在@ComponentScan可扫描到的包下。）

`RibbonRuleConfig.java`
```
/**
 * @author :jty
 * @date :20-7-31
 * @description :注入随机策略
 */
@Configuration
public class RibbonRuleConfig {
    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}
```
`AdminApp.java`
```
/**
 * @author :jty
 * @date :20-7-28
 * @description : @RibbonClient  name:该负载均衡策略生效对象（微服务提供者）微服务名，configuration：自定义配置类
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "user-server",configuration = RibbonRuleConfig.class)
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class);
    }
}
```

