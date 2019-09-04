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

    public static RTRaw error(RTException ex) {
        return new RTRaw()
                .setCode(RTCode.RT_EX_FAIL.getCode())
                .setMsg(RTCode.RT_EX_FAIL.getMsg())
                .setSubCode(ex.getCode())
                .setSubMsg(ex.getMsg())
                .setTraceId(MDC.get(SystemConstant.SYSTEM_TRACE_ID));
    }

    public static RTRaw error(Exception ex) {
        return new RTRaw()
                .setCode(RTCode.EX_FAIL.getCode())
                .setMsg(RTCode.EX_FAIL.getMsg())
                .setTraceId(MDC.get(SystemConstant.SYSTEM_TRACE_ID));
    }

    public static RTRaw limitError() {
        return new RTRaw()
                .setCode(RTCode.LIMIT_FAIL.getCode())
                .setMsg(RTCode.LIMIT_FAIL.getMsg())
                .setTraceId(MDC.get(SystemConstant.SYSTEM_TRACE_ID));
    }
}
