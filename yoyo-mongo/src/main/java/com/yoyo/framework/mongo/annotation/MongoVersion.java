package com.yoyo.framework.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 @Author:MrHuang
 @Date: 2019/9/5 15:02
 @DESC: TODO 乐观锁版本控制
 @VERSION: 1.0
 ***/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoVersion {
}
