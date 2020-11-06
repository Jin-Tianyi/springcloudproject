package com.jty.account.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author :jty
 * @date :20-9-10
 */
@Mapper
public interface AccountMapper {
    /**
     * 从用户账户中借出
     * @param userId
     * @param money
     * @return int
     */
    int debit(@Param(value = "userId") BigInteger userId, @Param(value = "money") BigDecimal money);
}
