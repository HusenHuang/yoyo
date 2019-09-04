package com.yoyo.framework.exception;

import com.yoyo.framework.api.RTCode;

/***
 @Author:MrHuang
 @Date: 2019/9/4 18:13
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class RTGetLockException extends RTException {

    public RTGetLockException() {
        super(RTCode.GET_LOCK_FAIL.getCode(), RTCode.GET_LOCK_FAIL.getMsg());
    }
}