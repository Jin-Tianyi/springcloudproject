package com.jty.storage.service;

import com.jty.storage.dao.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :jty
 * @date :20-9-10
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public int deduct(String commodityCode, int count) {
        return storageMapper.deduct(commodityCode, count);
    }
}
