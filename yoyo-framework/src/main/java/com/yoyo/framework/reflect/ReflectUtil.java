package com.yoyo.framework.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/***
 @Author:MrHuang
 @Date: 2019/9/4 16:42
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Slf4j
public class ReflectUtil {

    public static Field getField(Class<?> clazz,  Class<? extends Annotation> annotationCls) {
        return org.apache.commons.lang3.reflect.FieldUtils.getFieldsListWithAnnotation(clazz, annotationCls).get(0);
    }

    public static Object getFieldValue(Object object, Class<? extends Annotation> annotationCls)  {
        Field field = ReflectUtil.getField(object.getClass(), annotationCls);
        try {
            return field.get(object);
        } catch (IllegalAccessException ex) {
            log.error("ReflectUtil getFieldValue fail ", ex);
            throw new IllegalArgumentException(ex);
        }
    }

    public static String getFieldName(Object object, Class<? extends Annotation> annotationCls) {
        Field field = ReflectUtil.getField(object.getClass(), annotationCls);
        return field.getName();
    }
}
