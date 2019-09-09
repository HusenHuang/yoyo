package com.yoyo.framework.api;

import com.yoyo.framework.common.SystemConstant;
import com.yoyo.framework.redis.RedisUtils;
import com.yoyo.framework.reflect.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

/***
 @Author:MrHuang
 @Date: 2019/9/9 18:04
 @DESC: TODO 缓存版本实现
 @VERSION: 1.0
 ***/
@Slf4j
public class RTServiceCacheImpl<K,V> extends RTServiceImpl<K,V> {

    private String cacheKey;

    private int cacheExpireSecond;

    public RTServiceCacheImpl(String cacheKey, int cacheExpireSecond) {
        this.cacheKey = cacheKey;
        this.cacheExpireSecond = cacheExpireSecond;
    }

    public String getCacheKey(String id) {
        return this.cacheKey + "_" + id;
    }

    @Override
    public V add(V v) {
        V result = super.add(v);
        ReflectUtil.FieldNameValue nameValue = ReflectUtil.getFieldNameValue(result, Id.class);
        Assert.notNull(nameValue, "nameValue not null");
        boolean cacheResult = RedisUtils.setForString(getCacheKey(nameValue.getFieldValue().toString()), result, cacheExpireSecond);
        log.info("RTServiceCacheImpl cacheResult = {} result = {}", cacheResult, result);
        return result;
    }

    @Override
    public V get(K id) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        V result = RedisUtils.getObjectForString(getCacheKey(id.toString()), vClass);
        if (Objects.isNull(result)) {
            result = super.get(id);
            if (Objects.isNull(result)) {
                try {
                    result = vClass.newInstance();
                    RedisUtils.setForString(getCacheKey(id.toString()), result, 1000);
                }  catch (Exception e) {
                    log.error("RTServiceCacheImpl get fail", e);
                }
                return null;
            } else {
                RedisUtils.setForString(getCacheKey(id.toString()), result, cacheExpireSecond);
                return result;
            }
        } else {
            ReflectUtil.FieldNameValue fieldNameValue = ReflectUtil.getFieldNameValue(result, Id.class);
            if (Objects.isNull(fieldNameValue.getFieldValue())) {
               return null;
            } else {
               return result;
            }
        }

    }

    @Override
    public List<V> list(K... ids) {
        return super.list(ids);
    }

    @Override
    public boolean update(V v) {
        boolean result = super.update(v);
        if (result) {
            ReflectUtil.FieldNameValue nameValue = ReflectUtil.getFieldNameValue(v, Id.class);
            Assert.notNull(nameValue, "nameValue not null");
            boolean cacheResult = RedisUtils.setForString(getCacheKey(nameValue.getFieldValue().toString()), v, cacheExpireSecond);
            log.info("RTServiceCacheImpl  update cacheResult = {} result = {}", cacheResult, result);
        }
        return result;
    }

    @Override
    public boolean updateWithVersion(V v) {
        boolean result = super.updateWithVersion(v);
        if (result) {
            ReflectUtil.FieldNameValue nameValue = ReflectUtil.getFieldNameValue(v, Id.class);
            Assert.notNull(nameValue, "nameValue not null");
            boolean cacheResult = RedisUtils.setForString(getCacheKey(nameValue.getFieldValue().toString()), v, cacheExpireSecond);
            log.info("RTServiceCacheImpl  updateWithVersion cacheResult = {} result = {}", cacheResult, result);
        }
        return result;
    }

    @Override
    public boolean delete(K id) {
        boolean result = super.delete(id);
        if (result) {
            Boolean cacheResult = RedisUtils.delete(getCacheKey(id.toString()));
            log.info("RTServiceCacheImpl delete cacheResult = {} result = {}", cacheResult, result);
        }
        return result;
    }
}
