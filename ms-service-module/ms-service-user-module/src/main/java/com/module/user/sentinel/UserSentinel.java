package com.module.user.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author :jty
 * @date :20-9-9
 * @description :
 */
public class UserSentinel {
    public static String blockHandler(int num, BlockException e){
        return "/sentinel/resource blockHandler";
    }
    public static String fallback(int num){
        return "/sentinel/resource fallback";
    }
}
