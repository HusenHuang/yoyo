package com.yoyo.framework.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 @Author:MrHuang
 @Date: 2019/9/20 18:27
 @DESC: TODO 自动给CreateTime字段赋值
 @VERSION: 1.0
 ***/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoCreateTime {
}
