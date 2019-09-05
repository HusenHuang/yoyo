package com.yoyo.framework.api;

import com.yoyo.framework.common.SystemConstant;
import com.yoyo.framework.exception.RTException;
import org.slf4j.MDC;

/***
 @Author:MrHuang
 @Date: 2019/9/4 14:56
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class RTRawWrite {

    public static <T> RTRaw<T> success(T payload) {
        return new RTRaw<T>()
                .setCode(RTCode.SUCCESS.getCode())
                .setMsg(RTCode.SUCCESS.getMsg())
                .setTraceId(MDC.get(SystemConstant.SYSTEM_TRACE_ID))
                .setPayLoad(payload);
    }


    public static RTRaw error(int code, String msg, String subMsg) {
        return new RTRaw()
                .setCode(code)
                .setMsg(msg)
                .setSubMsg(subMsg)
                .setTraceId(MDC.get(SystemConstant.SYSTEM_TRACE_ID));
    }



}
