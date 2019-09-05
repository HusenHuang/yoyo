package com.yoyo.framework.api;

import lombok.Getter;
import lombok.ToString;

/***
 @Author:MrHuang
 @Date: 2019/9/3 15:45
 @DESC: TODO 统一响应码
 @VERSION: 1.0
 ***/
@Getter
@ToString
public enum RTCode {

    /**
     * RT:响应成功时
     */
    SUCCESS(0, "SUCCESS"),

    /**
     * 系统校验失败
     */
    REQ_PARAM_CHECK_ERROR(1, "系统校验失败"),

    /**
     * 系统链路异常
     */
    EX_FAIL(2, "系统出错了，稍后再试"),

    /**
     * 系统限流
     */
    LIMIT_FAIL(3, "系统限流，稍后再试"),

    /**
     * 系统获取锁失败
     */
    GET_LOCK_FAIL(4, "系统内部限制，请稍后再试"),

    /**
     * 请求体异常
     */
    REQ_BODY_CHECK_ERROR(5, "请求非法"),
    ;


    RTCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    private String subMsg;
}
