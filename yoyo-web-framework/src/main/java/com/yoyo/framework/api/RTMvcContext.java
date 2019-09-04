package com.yoyo.framework.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/6/24 10:52
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RTMvcContext implements Serializable {

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * 客户端请求IP
     */
    private String clientIp;

    /**
     * 系统TraceId
     */
    private String traceId;

    /**
     * 系统SpanId
     */
    private String spanId;

    /**
     * 用户TokenId
     */
    private String tokenId;

    /**
     * 客户端请求时间戳
     */
    private Long clientReqTime;

    /**
     * 客户端请求类型
     */
    private String clientReqType;
}