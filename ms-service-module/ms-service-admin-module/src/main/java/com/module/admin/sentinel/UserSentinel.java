package com.module.admin.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.base.entity.Result;

/**
 * @author :jty
 * @date :20-9-9
 * @description : sentinel 服务降级、熔断方法实现
 */
public class UserSentinel {
    public static Result blockHandler(int userId, BlockException e){
        return new Result(-200, "sentinel 服务降级或熔断",null);
    }
    public static Result fallback(int userId, BlockException e){
        return new Result(-200, "sentinel 服务降级或熔断",null);
    }
}
