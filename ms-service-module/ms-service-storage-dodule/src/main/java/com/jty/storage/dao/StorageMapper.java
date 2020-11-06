package com.jty.storage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @author :jty
 * @date :20-9-10
 */
@Mapper
public interface StorageMapper {
    /**
     * 扣除存储数量
     * @param commodityCode
     * @param count
     * @return int
     */
    int deduct(@Param(value = "commodityCode") String commodityCode, @Param(value = "count") int count);
}
