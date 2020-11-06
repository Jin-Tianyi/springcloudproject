package com.base.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author :jty
 * @date :20-9-10
 * `id` int(11) NOT NULL AUTO_INCREMENT,
 * `user_id` varchar(255) DEFAULT NULL,
 * `money` int(11) DEFAULT 0,
 */
@Setter
@Getter
@Data
@NoArgsConstructor
public class Account {
    private BigInteger id;
    private BigInteger userId;
    private BigDecimal money;
}
