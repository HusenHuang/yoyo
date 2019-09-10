package com.yoyo.framework.api;

import com.yoyo.framework.redis.RedisUtils;
import com.yoyo.framework.reflect.ReflectUtil;
import com.yoyo.framework.utils.AsyncExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static final int SHORT_CACHE_SECOND = 1000;

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
                    RedisUtils.setForString(getCacheKey(id.toString()), result, SHORT_CACHE_SECOND);
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
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        List<String> keys = Arrays.stream(ids).map(s -> this.getCacheKey(s.toString())).collect(Collectors.toList());
        List<V> vs = RedisUtils.multiGetForString(vClass, keys);
        List<K> asyncList = new ArrayList<>();
        for (int i = 0; i < ids.length; i ++) {
            V v = vs.get(i);
            if (Objects.isNull(v)) {
                // 收集起来,异步缓存起来
                asyncList.add(ids[i]);
            } else {
                ReflectUtil.FieldNameValue fieldNameValue = ReflectUtil.getFieldNameValue(v, Id.class);
                if (Objects.isNull(fieldNameValue.getFieldValue())) {
                    // 如果是空缓存,设置为null
                    vs.set(i, null);
                }
            }
        }
        AsyncExecutor.execute(() -> {
            for (K id : asyncList) {
                // 异步刷新缓存
                this.get(id);
            }
        });
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
