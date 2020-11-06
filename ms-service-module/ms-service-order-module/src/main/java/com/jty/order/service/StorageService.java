package com.jty.order.service;

import com.base.entity.Result;
import com.jty.order.service.impl.StorageServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author :jty
 * @date :20-9-10
 */
@Component
@FeignClient(value = "storage-server", fallback = StorageServiceImpl.class)
public interface StorageService {
    /**
     * 调用库存接口减库存
     *
     * @param commodityCode
     * @param count
     * @return Result
     */
    @PostMapping(value = "/storage/debit")
    Result debit(@RequestParam(value = "commodityCode") String commodityCode, @RequestParam(value = "count") int count);
}
