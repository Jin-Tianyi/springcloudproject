package com.jty.order.dao;

import com.base.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :jty
 * @date :20-9-10
 */
@Mapper
public interface OrderMapper {
    /**
     * 创建订单
     * @param order
     * @return int
     */
    int create(Order order);
}
