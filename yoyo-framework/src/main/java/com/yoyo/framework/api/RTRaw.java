package com.yoyo.framework.api;

import lombok.Data;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/3 16:03
 @DESC: TODO 统一响应体
 @VERSION: 1.0
 ***/
@Data
public class RTRaw<T> implements Serializable {

    /**
     * 网关层code
     */
    private Integer code;

    /**
     * 业务层code
     * 当异常时候才返回
     */
    private Integer subCode;

    /**
     * 网关层msg
     */
    private String msg;

    /**
     * 业务层msg
     * 当异常时候才返回
     */
    private String subMsg;

    /**
     * 业务响应体payLoad
     */
    private T payLoad;

    /**
     * 链路追踪消息ID
     */
    private String traceId;

    /**
     * 异常信息内容
     */
    private String throwableMessage;
}
