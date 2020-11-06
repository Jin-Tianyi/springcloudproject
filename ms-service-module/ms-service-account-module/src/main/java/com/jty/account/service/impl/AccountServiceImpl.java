package com.jty.account.service.impl;

import com.jty.account.dao.AccountMapper;
import com.jty.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author :jty
 * @date :20-9-10
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public int debit(BigInteger userId, BigDecimal money) {
        return accountMapper.debit(userId,money);
    }
}
