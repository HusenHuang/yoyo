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

    public RTException(String message) {
        super(message);
    }
}
