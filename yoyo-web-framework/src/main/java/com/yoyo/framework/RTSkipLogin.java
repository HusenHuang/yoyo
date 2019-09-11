package com.yoyo.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 @Author:MrHuang
 @Date: 2019/9/11 17:46
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RTSkipLogin {
}
