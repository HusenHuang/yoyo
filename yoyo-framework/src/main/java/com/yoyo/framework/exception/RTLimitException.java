package com.yoyo.framework.exception;

import com.yoyo.framework.api.RTCode;

/***
 @Author:MrHuang
 @Date: 2019/9/4 18:09
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class RTLimitException extends RTException {

    public RTLimitException() {
        super(RTCode.LIMIT_FAIL.getCode(), RTCode.LIMIT_FAIL.getMsg());
    }
}
