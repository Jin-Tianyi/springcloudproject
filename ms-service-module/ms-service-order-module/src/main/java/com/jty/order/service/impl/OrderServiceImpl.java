package com.jty.order.service.impl;

import com.base.entity.Order;
import com.base.entity.Result;
import com.jty.order.dao.OrderMapper;
import com.jty.order.service.AccountService;
import com.jty.order.service.OrderService;
import com.jty.order.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @author :jty
 * @date :20-9-10
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StorageService storageService;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order create(Order order) {
        order.setMoney(order.getMoney());
        orderMapper.create(order);
        return order;
    }

    @Override
    public Result order(Order order) {
        try {
            Object obj0 = create(order);
            Object obj1 = accountService.debit(order.getUserId(), order.getMoney());
            Object obj2 = storageService.debit(order.getCommodityCode(), order.getCount());
        } catch (Exception e) {
            return new Result(-200, "操作失败");
        } finally {
            //
        }

        return new Result(200, "操作成功");
    }
}
