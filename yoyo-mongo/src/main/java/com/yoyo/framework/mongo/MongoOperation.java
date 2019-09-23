package com.yoyo.framework.mongo;

import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.mongo.annotation.MongoCreateTime;
import com.yoyo.framework.mongo.annotation.MongoUpdateTime;
import com.yoyo.framework.reflect.ReflectUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/***
 @Author:MrHuang
 @Date: 2019/9/21 13:38
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Component
public class MongoOperation {

    /**
     * 生成创建时间字段
     * @param v
     */
    public <V> void builderCreateTime(V v) {
        Field field = ReflectUtil.getField(v.getClass(), MongoCreateTime.class);
        builderTime(field, v, MongoCreateTime.class);
    }

    /**
     * 生成更新时间字段
     * @param v
     */
    public <V> void builderUpdateTime(V v) {
        Field field = ReflectUtil.getField(v.getClass(), MongoUpdateTime.class);
        builderTime(field, v, MongoUpdateTime.class);
    }

    /**
     * 生成时间方法
     * @param v
     */
    private <V> void builderTime(Field field, V v, Class<? extends Annotation> annotationClass) {
        if (Objects.nonNull(field)) {
            Class<?> type = field.getType();
            Annotation annotation = field.getAnnotation(annotationClass);
            MongoTimeType value = (MongoTimeType)AnnotationUtils.getValue(annotation);
            if (MongoTimeType.TIME == value) {
                if (type == String.class) {
                    ReflectUtil.setFieldValue(v, field.getName(), DateUtils.nowTime());
                } else if (type == LocalDateTime.class) {
                    ReflectUtil.setFieldValue(v, field.getName(), LocalDateTime.now());
                } else {
                    throw new IllegalArgumentException("field type need String or LocalDateTime");
                }
            } else {
                if (type == String.class) {
                    ReflectUtil.setFieldValue(v, field.getName(), DateUtils.nowDate());
                } else if (type == LocalDate.class) {
                    ReflectUtil.setFieldValue(v, field.getName(), LocalDate.now());
                } else {
                    throw new IllegalArgumentException("field type need String or LocalDate");
                }
            }
        }
    }
}
