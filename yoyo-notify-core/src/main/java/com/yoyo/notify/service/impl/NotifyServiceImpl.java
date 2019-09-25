package com.yoyo.notify.service.impl;

import com.yoyo.notify.INotifyService;

import java.util.concurrent.TimeUnit;

/***
 @Author:MrHuang
 @Date: 2019/9/24 17:39
 @DESC: TODO
 @VERSION: 1.0
 ***/
@org.apache.dubbo.config.annotation.Service(timeout = 10)
@org.springframework.stereotype.Service
public class NotifyServiceImpl implements INotifyService {
    @Override
    public String echo() {
        return "hello notify service impl";
    }
}
