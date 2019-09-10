package com.yoyo.framework.api;

/***
 @Author:MrHuang
 @Date: 2019/6/24 10:50
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class RTMvcContextHodler {

    private static final ThreadLocal<RTMvcContext> CONTEXT = new ThreadLocal<>();

    public static RTMvcContext getMvcContext() {
        return CONTEXT.get();
    }

    protected static void setMvcContext(RTMvcContext bean) {
        CONTEXT.set(bean);
    }

    protected static void removeMvcContext() {
        CONTEXT.remove();
    }
}
