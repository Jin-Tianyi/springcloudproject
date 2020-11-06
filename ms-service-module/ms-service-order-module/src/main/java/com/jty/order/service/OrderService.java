package com.jty.order.service;

import com.base.entity.Order;
import com.base.entity.Result;

/**
 * @author :jty
 * @date :20-9-10
 */
public interface OrderService {
    /**
     * 创建订单
     * @param order
     * @return Order
     */
    Order create(Order order);

    /**
     * 下订单
     * @param order
     * @return
     */
    Result order(Order order);
}
