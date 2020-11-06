package com.jty.order.service.impl;

import com.base.entity.Result;
import com.jty.order.service.StorageService;
import org.springframework.stereotype.Component;

/**
 * @author :jty
 * @date :20-9-10
 */
@Component
public class StorageServiceImpl implements StorageService {
    @Override
    public Result debit(String commodityCode, int count) {
        return new Result(-200,"feign 调用失败");
    }
}
