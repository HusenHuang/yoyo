package com.yoyo.framework.api;

import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.exception.RTGetLockException;
import com.yoyo.framework.exception.RTLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 @Author:MrHuang
 @Date: 2019/6/14 11:38
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RestControllerAdvice
@Slf4j
public class RTMvcExceptionHandlerAdvice {

    /**
     * toast提示异常，这种错误一般是提示，认为非异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RTException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerRTException(RTException ex) {
        return RTRawWrite.error(RTCode.REQ_PARAM_CHECK_ERROR.getCode(), RTCode.REQ_PARAM_CHECK_ERROR.getMsg(), ex.getMessage());
    }

    /**
     * 这种错误一般是提示，认为非异常
     * 参数校验异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return RTRawWrite.error(RTCode.REQ_PARAM_CHECK_ERROR.getCode(), RTCode.REQ_PARAM_CHECK_ERROR.getMsg(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 限流异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RTLimitException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerRTLimitException(RTLimitException ex) {
        log.error("ExceptionHandlerAdvice handler RTLimitException ", ex);
        return RTRawWrite.error(RTCode.LIMIT_FAIL.getCode(), RTCode.LIMIT_FAIL.getMsg(), ex.getMessage());
    }


    /**
     * 并发获取锁异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RTGetLockException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerRTGetLockException(RTGetLockException ex) {
        log.error("ExceptionHandlerAdvice handler RTGetLockException ", ex);
        return RTRawWrite.error(RTCode.GET_LOCK_FAIL.getCode(), RTCode.GET_LOCK_FAIL.getMsg(), ex.getMessage());
    }


    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerException(Exception ex) {
        log.error("ExceptionHandlerAdvice handler Exception ", ex);
        return RTRawWrite.error(RTCode.EX_FAIL.getCode(), RTCode.EX_FAIL.getMsg(), RTCode.EX_FAIL.getMsg());
    }

    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("ExceptionHandlerAdvice handler HttpMessageNotReadableException ", ex);
        return RTRawWrite.error(RTCode.REQ_BODY_CHECK_ERROR.getCode(), RTCode.REQ_BODY_CHECK_ERROR.getMsg(), RTCode.REQ_BODY_CHECK_ERROR.getMsg());
    }




}
