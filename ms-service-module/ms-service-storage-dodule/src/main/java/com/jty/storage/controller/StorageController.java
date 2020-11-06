package com.jty.storage.controller;

import com.base.entity.Order;
import com.base.entity.Result;
import com.jty.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author :jty
 * @date :20-9-10
 */
@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping(value = "/storage/debit")
    public Result debit (Order order) {
        int rows = storageService.deduct(order.getCommodityCode(), order.getCount());
        if (rows > 0) {
            return new Result(200, "操作成功,rows =" + rows, null);
        } else {
            return new Result(-200, "操作失败", null);
        }
    }
}
