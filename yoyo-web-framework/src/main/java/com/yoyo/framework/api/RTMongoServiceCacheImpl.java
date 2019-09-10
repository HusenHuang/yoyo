package com.yoyo.framework.api;

import com.yoyo.framework.redis.RedisUtils;
import com.yoyo.framework.reflect.ReflectUtil;
import com.yoyo.framework.utils.AsyncExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 @Author:MrHuang
 @Date: 2019/9/9 18:04
 @DESC: TODO
 MongoDB业务CRUD
 缓存版本实现
 @VERSION: 1.0
 ***/
@Slf4j
public class RTMongoServiceCacheImpl<K,V> extends RTMongoServiceImpl<K,V> {

    private String cacheKey;

    private int cacheExpireSecond;

    private static final int SHORT_CACHE_SECOND = 1000;

    public RTMongoServiceCacheImpl(String cacheKey, int cacheExpireSecond) {
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
        log.info("RTMongoServiceCacheImpl cacheResult = {} result = {}", cacheResult, result);
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
                    log.error("RTMongoServiceCacheImpl get fail", e);
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
    public List<V> list(List<K> ids) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        // 组装缓存Key
        List<String> keys = ids.stream().map(s -> this.getCacheKey(s.toString())).collect(Collectors.toList());
        // 批量获取缓存值
        List<V> result = RedisUtils.multiGetForString(vClass, keys);
        List<K> asyncKeyList = new ArrayList<>();
        List<V> cacheResult = new ArrayList<>();
        for (int i = 0; i < ids.size(); i ++) {
            V v = result.get(i);
            if (Objects.isNull(v)) {
                // 收集起来,异步缓存起来
                asyncKeyList.add(ids.get(i));
            } else {
                ReflectUtil.FieldNameValue nameValue = ReflectUtil.getFieldNameValue(v, Id.class);
                Assert.notNull(nameValue, "nameValue not null ...");
                if (Objects.isNull(nameValue.getFieldValue())) {
                    // 如果是空缓存，不做处理
                } else {
                    // 如果是非空缓存，加入集合
                    cacheResult.add(result.get(i));
                }
            }
        }

        // 缓存中未命中的Key
        if (!asyncKeyList.isEmpty()) {
            List<V> dbResult = super.list(asyncKeyList);
            cacheResult.addAll(dbResult);
            // 异步刷新缓存
            AsyncExecutor.execute(() -> {
                for (K id : asyncKeyList) {
                    // 异步刷新缓存
                    this.get(id);
                }
            });
        }
        return cacheResult;
    }

    @Override
    public boolean update(V v) {
        boolean result = super.update(v);
        if (result) {
            ReflectUtil.FieldNameValue nameValue = ReflectUtil.getFieldNameValue(v, Id.class);
            Assert.notNull(nameValue, "nameValue not null");
            boolean cacheResult = RedisUtils.setForString(getCacheKey(nameValue.getFieldValue().toString()), v, cacheExpireSecond);
            log.info("RTMongoServiceCacheImpl  update cacheResult = {} result = {}", cacheResult, result);
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
            log.info("RTMongoServiceCacheImpl  updateWithVersion cacheResult = {} result = {}", cacheResult, result);
        }
        return result;
    }

    @Override
    public boolean delete(K id) {
        boolean result = super.delete(id);
        if (result) {
            Boolean cacheResult = RedisUtils.delete(getCacheKey(id.toString()));
            log.info("RTMongoServiceCacheImpl delete cacheResult = {} result = {}", cacheResult, result);
        }
        return result;
    }
}
