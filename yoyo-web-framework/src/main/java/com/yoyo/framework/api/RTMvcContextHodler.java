package com.yoyo.framework.api;

/***
 @Author:MrHuang
 @Date: 2019/6/24 10:50
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class RTMvcContextHodler {

    private static final ThreadLocal<RTMvcContext> CONTEXT = new ThreadLocal<>();

    public static RTMvcContext getWebRequest() {
        return CONTEXT.get();
    }

    protected static void setWebRequest(RTMvcContext bean) {
        CONTEXT.set(bean);
    }

    protected static void removeWebRequest() {
        CONTEXT.remove();
    }
}
