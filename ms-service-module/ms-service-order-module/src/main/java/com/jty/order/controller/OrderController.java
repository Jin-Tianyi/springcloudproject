package com.jty.order.controller;

import com.base.entity.Order;
import com.base.entity.Result;
import com.jty.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author :jty
 * @date :20-9-10
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/order")
    public Result order(Order order) {
        return orderService.order(order);
    }
}
