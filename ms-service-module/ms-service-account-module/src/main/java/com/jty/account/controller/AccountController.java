package com.jty.account.controller;

import com.base.entity.Order;
import com.base.entity.Result;
import com.jty.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :jty
 * @date :20-9-10
 */
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/account/debit")
    public Result debit(Order order) {
        int rows = accountService.debit(order.getUserId(), order.getMoney());
        if (rows > 0) {
            return new Result(200, "操作成功,rows =" + rows, null);
        } else {
            return new Result(-200, "操作失败", null);
        }
    }
}
