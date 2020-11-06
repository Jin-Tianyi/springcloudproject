package com.jty.storage.service;

import java.math.BigDecimal;

/**
 * @author :jty
 * @date :20-9-10
 */
public interface StorageService {
    /**
     * 扣除存储数量
     * @param commodityCode
     * @param count
     * @return int
     */
    int deduct(String commodityCode, int count);
}
