package com.yoyo.framework.exception;

/***
 @Author:MrHuang
 @Date: 2019/9/3 17:11
 @DESC: TODO RTException
 @VERSION: 1.0
 ***/
public class RTException extends RuntimeException {

    public RTException() {
        super();
    }

    public RTException(String message) {
        super(message);
    }


    public RTException(String message, Throwable cause) {
        super(message, cause);
    }


}
