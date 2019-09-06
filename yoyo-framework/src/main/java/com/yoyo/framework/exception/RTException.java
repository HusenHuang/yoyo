package com.yoyo.framework.exception;

import lombok.Getter;
import lombok.ToString;

/***
 @Author:MrHuang
 @Date: 2019/9/3 17:11
 @DESC: TODO RTException
 @VERSION: 1.0
 ***/
@Getter
@ToString
public class RTException extends RuntimeException {

    private int code;

    public RTException(String message) {
        super(message);
    }

    public  RTException(Integer code ,String message) {
        this(message);
        this.code = code;
    }
}
