package com.base.entity;

import lombok.*;

/**
 * @author :jty
 * @date :20-7-28
 * @description :
 */
@NoArgsConstructor
@Getter
@Setter
@Data
public class Result <T>{
    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
        this.data = null;
    }
    public Result(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
