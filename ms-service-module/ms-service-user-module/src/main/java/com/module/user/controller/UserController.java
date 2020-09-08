package com.module.user.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.base.dao.User;
import com.base.entity.Result;
import com.module.user.mapper.UserMapper;
import com.module.user.sentinel.UserSentinel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author :jty
 * @date :20-7-20
 * @description :用户模块
 */
@RestController
@RefreshScope
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${server.port}")
    String serverPort;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private static int i = 0;

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


    /** 测试sentinel 硬编码 */
    @GetMapping(value = "/sentinel/coding", produces = "application/json;charset=utf-8")
    public String sentinelConding(@RequestParam(value = "num") int num){
        //规则配置
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                // 若需要配置例外项，则传入的参数只支持基本类型
                // EntryType 代表流量类型，其中系统规则只对 IN 类型的埋点生效
                // count 大多数情况都填 1，代表统计为一次调用
                // 热点规则 entry = SphU.entry(resourceName, EntryType.IN, 1, paramA, paramB)
                entry = SphU.entry("/sentinel/coding");
                /*您的业务逻辑 - 开始*/
                logger.info("---------------sentinel 开始---------------");
                logger.info("---------------{}---------------",i++);
                int c = 10 / num;
                logger.info("---------------sentinel 结束---------------");
                return "/sentinel/coding success";
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e1) {
                /*流控逻辑处理 - 开始*/
                logger.info("---------------sentinel BlockException-----------");
                return "/sentinel/coding flow limite "+e1.getMessage();
                /*流控逻辑处理 - 结束*/
            } catch (Exception e){
                /* fallback 方法逻辑处理 - 开始*/
                logger.info("---------------sentinel fallback-----------");
                return "/sentinel/coding fallback";
                /* fallback 方法逻辑处理 - 结束*/
            } finally{
                if (entry != null) {
                    //注意：exit 的时候也一定要带上对应的参数，否则可能会有统计错误
                    // entry.exit(1, paramA, paramB)
                    entry.exit();
                }
            }
        }
    }
    /** 测试sentinel 控制台配置 */
    @GetMapping(value = "/sentinel/config", produces = "application/json;charset=utf-8")
    public String sentinelConfig(@RequestParam(value = "num") int num){
        logger.info("---------------sentinel 开始---------------");
        int c = 10/num;
        logger.info("---------------sentinel 结束---------------");
        return "/sentinel/config success";
    }
    /** 测试sentinel 控制台配置 */
    @GetMapping(value = "/sentinel/resource", produces = "application/json;charset=utf-8")
    @SentinelResource(value = "sentinel/resource",blockHandlerClass = UserSentinel.class,blockHandler = "blockHandler",fallback = "fallback")
    public String sentinelResource(@RequestParam(value = "num") int num){
        logger.info("---------------sentinel 开始---------------");
        int c = 10/num;
        logger.info("---------------sentinel 结束---------------");
        return "/sentinel/resource success";
    }
    /** 规则配置 */
    private void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        //流控规则或其他规则
        FlowRule rule = new FlowRule();
        rule.setResource("/sentinel/coding");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置规则参数，如每秒访问3次超过三次限流
        rule.setCount(3);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
