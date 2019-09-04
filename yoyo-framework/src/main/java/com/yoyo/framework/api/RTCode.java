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
     * RT_EX:响应失败时
     */
    RT_EX_FAIL(1, "RT_EX_FAIL"),


    /**
     * EX: 响应失败
     */
    EX_FAIL(2, "EX_FAIL"),

    ;

    RTCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;
}
