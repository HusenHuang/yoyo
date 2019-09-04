package com.yoyo.framework.api;

import lombok.Getter;
import lombok.ToString;

/***
 @Author:MrHuang
 @Date: 2019/9/3 15:56
 @DESC: TODO 统一响应业务码
 @VERSION: 1.0
 ***/
@Getter
@ToString
public enum RTSubCode {

    /**
     * RT:响应成功时
     */
    SUCCESS(0, "SUCCESS"),

    /**
     * RT:响应失败时
     */
    FAIL(2, "EX_FAIL"),
    ;

    RTSubCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;
}
