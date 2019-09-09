package com.yoyo.framework.common;

import java.time.ZoneOffset;

/***
 @Author:MrHuang
 @Date: 2019/7/12 15:23
 @DESC: TODO 系统常量池
 @VERSION: 1.0
 ***/
public class SystemConstant {

    /**
     * HTTP ContentType 中 JSON
     */
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    /**
     * HTTP ContentType 中 URLENCODED
     */
    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";

    /**
     * HTTP STATUS OK
     */
    public static final int HTTP_CODE_OK = 200;

    /**
     * TIME FORMAT
     */
    public static final String FORMAT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * DATE FORMAT
     */
    public static final String FORMAT_DATE_PATTERN = "yyyy-MM-dd";


    /**
     * 系统TraceId
     */
    public static final String SYSTEM_TRACE_ID = "X-B3-TraceId";

    /**
     * 系统SpanId
     */
    public static final String SYSTEM_SPAN_ID = "X-B3-SpanId";

    /**
     * 请求的TokenId
     */
    public static final String HEADER_TOKEN_ID = "tokenId";

    /**
     * 请求的类型
     */
    public static final String HEADER_RQUEST_TYPE = "clientReqType";

    /**
     * 系统-空
     */
    public static final String SYSTEM_EMPTY = "";

    /**
     * 系统时区-东八区
     */
    public static final ZoneOffset SYSTEM_ZO = ZoneOffset.of("+8");

    /**
     * 系统逻辑删除值
     */
    public static final Integer LOGIC_DELETE_STATUS = -1;
}
