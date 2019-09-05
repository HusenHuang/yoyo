package com.yoyo.framework.reflect;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.Serializable;
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
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException ex) {
            log.error("ReflectUtil getFieldValue fail ", ex);
            throw new IllegalArgumentException(ex);
        }
    }

    public static String getFieldName(Object object, Class<? extends Annotation> annotationCls) {
        Field field = ReflectUtil.getField(object.getClass(), annotationCls);
        field.setAccessible(true);
        return field.getName();
    }

    public static FieldNameValue getFieldNameValue(Object object, Class<? extends Annotation> annotationCls) {
        Field field = ReflectUtil.getField(object.getClass(), annotationCls);
        field.setAccessible(true);
        try {
            return new FieldNameValue()
                    .setFieldName(field.getName())
                    .setFieldValue(field.get(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object object, String fieldName, Object newFieldValue) {
        Field field = FieldUtils.getField(object.getClass(), fieldName, true);
        try {
            field.set(object, newFieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Data
    @Accessors(chain = true)
    public static class FieldNameValue implements Serializable {
        private String fieldName;

        private Object fieldValue;
    }
}
