package com.yoyo.framework.zookeeper.annotation;

import org.springframework.core.annotation.AliasFor;

/***
 @Author:MrHuang
 @Date: 2019/9/17 11:54
 @DESC: TODO
 @VERSION: 1.0
 ***/
public @interface DistributedLock {

    @AliasFor("lockPath")
    String value() default "";

    @AliasFor("value")
    String lockPath() default "";

    int expireSecond() default 2;
}
