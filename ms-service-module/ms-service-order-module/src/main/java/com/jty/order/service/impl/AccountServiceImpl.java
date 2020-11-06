package com.jty.order.service.impl;

import com.base.entity.Result;
import com.jty.order.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author :jty
 * @date :20-9-10
 */
@Component
public class AccountServiceImpl implements AccountService {
    @Override
    public Result debit(BigInteger userId, BigDecimal money) {
        return new Result(-200, "feign 调用失败");
    }
}
