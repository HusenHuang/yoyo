package com.yoyo.framework.zookeeper.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 @Author:MrHuang
 @Date: 2019/9/17 11:54
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZKDistributedLock {

    @AliasFor("lockPath")
    String value() default "";

    @AliasFor("value")
    String lockPath() default "";

    int expireSecond() default 2;
}
