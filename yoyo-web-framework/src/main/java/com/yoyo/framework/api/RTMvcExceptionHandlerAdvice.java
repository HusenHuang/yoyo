package com.yoyo.framework.api;

import com.yoyo.framework.exception.RTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        return RTRawWrite.error(RTCode.RT_EX_FAIL.getCode(), RTCode.RT_EX_FAIL.getMsg(), ex.getMessage());
    }


    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerException(Exception ex) {
        log.error("ExceptionHandlerAdvice handler exception ", ex);
        return RTRawWrite.error(RTCode.EX_FAIL.getCode(), RTCode.EX_FAIL.getMsg(), null);
    }


    /**
     * 参数校验异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.OK)
    public RTRaw handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return RTRawWrite.error(RTCode.RT_EX_FAIL.getCode(), RTCode.RT_EX_FAIL.getMsg(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


}
