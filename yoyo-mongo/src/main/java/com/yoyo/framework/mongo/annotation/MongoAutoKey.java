package com.yoyo.framework.mongo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 @Author:MrHuang
 @Date: 2019/9/20 19:57
 @DESC: TODO mongo自增逐渐
 @VERSION: 1.0
 ***/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoAutoKey {
}
