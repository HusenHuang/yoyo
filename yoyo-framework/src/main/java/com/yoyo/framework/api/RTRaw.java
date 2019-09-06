package com.yoyo.framework.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/3 16:03
 @DESC: TODO 统一响应体
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RTRaw<T> implements Serializable {

    /**
     * 业务CODE
     */
    private Integer code;

    /**
     * 业务MSG
     */
    private String msg;

    /**
     * 业务SUB CODE
     */
    private Integer subCode;

    /**
     * 业务SUB MSG
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
}
