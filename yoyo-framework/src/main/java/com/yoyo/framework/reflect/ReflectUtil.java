package com.yoyo.framework.reflect;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

/***
 @Author:MrHuang
 @Date: 2019/9/4 16:42
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Slf4j
public class ReflectUtil {

    /**
     * 根据注解获取Field
     * @param clazz
     * @param annotationCls
     * @return
     */
    public static Field getField(Class<?> clazz,  Class<? extends Annotation> annotationCls) {
        return org.apache.commons.lang3.reflect.FieldUtils.getFieldsListWithAnnotation(clazz, annotationCls).get(0);
    }

    /**
     * 根据注解获取FieldValue
     * @param object
     * @param annotationCls
     * @return
     */
    public static Object getFieldValue(Object object, Class<? extends Annotation> annotationCls)  {
        return  Optional.ofNullable(getFieldNameValue(object, annotationCls)).map(FieldNameValue::getFieldValue).orElse(null);
    }

    /**
     * 根据注解获取FieldName
     * @param object
     * @param annotationCls
     * @return
     */
    public static String getFieldName(Object object, Class<? extends Annotation> annotationCls) {
        return Optional.ofNullable(getFieldNameValue(object, annotationCls)).map(FieldNameValue::getFieldName).orElse(null);
    }

    /**
     * 根据注解获取FieldNameValue
     * @param object
     * @param annotationCls
     * @return
     */
    public static FieldNameValue getFieldNameValue(Object object, Class<? extends Annotation> annotationCls) {
        Field field = ReflectUtil.getField(object.getClass(), annotationCls);
        field.setAccessible(true);
        try {
            return new FieldNameValue().setFieldName(field.getName()).setFieldValue(field.get(object));
        } catch (IllegalAccessException e) {
            log.error("ReflectUtil getFieldNameValue error", e);
        }
        return null;
    }

    /**
     * 根据注解获取FieldNameValue
     * @param object
     * @param fieldName
     * @param newFieldValue
     * @return
     */
    public static void setFieldValue(Object object, String fieldName, Object newFieldValue) {
        Field field = FieldUtils.getField(object.getClass(), fieldName, true);
        try {
            field.set(object, newFieldValue);
        } catch (IllegalAccessException e) {
            log.error("ReflectUtil setFieldValue error", e);
        }
    }

    @Data
    @Accessors(chain = true)
    public static class FieldNameValue implements Serializable {
        private String fieldName;

        private Object fieldValue;
    }
}
