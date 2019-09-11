package com.yoyo.framework.api;

import com.yoyo.framework.RTSkipLogin;
import com.yoyo.framework.auth.JwtUtils;
import com.yoyo.framework.common.SystemConstant;
import com.yoyo.framework.exception.RTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 @Author:MrHuang
 @Date: 2019/9/11 17:41
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Slf4j
public class RTMvcLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是SpringMVC请求
        boolean result = true;
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RTSkipLogin methodAnnotation = handlerMethod.getMethodAnnotation(RTSkipLogin.class);
            if (methodAnnotation != null) {
                result = true;
            } else {
                String tokenId = request.getHeader(SystemConstant.HEADER_TOKEN_ID);
                result = tokenId != null && JwtUtils.checkToken(tokenId);
            }
        }
        log.info("{} preHadnle result = {}", "[RTMvcLoginInterceptor]", result);
        if (!result) {
            throw new RTException("请登录再试");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("{} postHandle ", "[RTMvcLoginInterceptor]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("{} afterCompletion ", "[RTMvcLoginInterceptor]");
    }
}
