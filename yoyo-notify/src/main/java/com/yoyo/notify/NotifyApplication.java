package com.yoyo.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/***
 @Author:MrHuang
 @Date: 2019/9/24 17:34
 @DESC: TODO
 @VERSION: 1.0
 ***/
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableScheduling
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@ComponentScans(value = {
        @ComponentScan("com.yoyo.framework")
})
@EnableDiscoveryClient
public class NotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifyApplication.class, args);
    }
}
