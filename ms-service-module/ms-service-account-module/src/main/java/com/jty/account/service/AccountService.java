package com.jty.account.service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author :jty
 * @date :20-9-10
 */
public interface AccountService {
    /**
     * 从用户账户中借出
     * @param userId
     * @param money
     */
    int debit(BigInteger userId, BigDecimal money);
}
