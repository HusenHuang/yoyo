package com.yoyo.framework.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/***
 @Author:MrHuang
 @Date: 2019/9/3 16:01
 @DESC: TODO 统一请求体
 @VERSION: 1.0
 ***/
@Data
public class RQRaw<T> implements Serializable {

    /**
     * YOYO_MANAGER:    YOYO后台系统
     * YOYO_WEB_H5:     YOYO_WEB_H5
     * YOYO_WX:         YOYO_WX_H5
     * YOYO_WXA:        YOYO_WXA
     */
    private String source;

    /**
     * 请求体数据
     */
    private T payload;
}
