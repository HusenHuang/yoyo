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

    private String msg;

    public RTException(int code ,String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public RTException(int code, Throwable cause) {
        super(cause);
        this.code = code;
        this.msg = cause.getMessage();
    }


}
