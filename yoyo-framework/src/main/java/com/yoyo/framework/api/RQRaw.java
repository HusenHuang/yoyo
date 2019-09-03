package com.yoyo.framework.api;

import lombok.Data;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/3 16:01
 @DESC: TODO 统一请求体
 @VERSION: 1.0
 ***/
@Data
public class RQRaw<T> implements Serializable {

    private String source;

    private T payload;
}
