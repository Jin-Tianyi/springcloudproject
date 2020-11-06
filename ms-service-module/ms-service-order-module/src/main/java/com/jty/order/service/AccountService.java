package com.jty.order.service;

import com.base.entity.Result;
import com.jty.order.service.impl.AccountServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author :jty
 * @date :20-9-10
 */
@Component
@FeignClient(value="account-server",fallback = AccountServiceImpl.class)
public interface AccountService {
    /**
     *
     * @param userId
     * @param money
     * @return
     */
    @PostMapping(value = "/account/debit")
    Result debit(@RequestParam(value = "userId") BigInteger userId, @RequestParam(value = "money") BigDecimal money);
}
