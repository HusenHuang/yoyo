package com.yoyo.framework.api;

import com.google.common.collect.Maps;
import com.yoyo.framework.mongo.MongoRepository;
import com.yoyo.framework.reflect.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 @Author:MrHuang
 @Date: 2019/9/9 11:28
 @DESC: TODO
 Mongo业务CRUD
 非缓存版本实现
 @VERSION: 1.0
 ***/
public class RTMongoServiceImpl<K,V> implements IRTService<K,V> {

    @Autowired
    private MongoRepository<K,V> dao;

    @Override
    public V add(V v) {
        return dao.insert(v);
    }

    @Override
    public V get(K id) {
        return dao.findById(id);
    }

    @Override
    public List<V> list(K... ids) {
        return this.list(Arrays.asList(ids));
    }

    @Override
    public List<V> list(List<K> ids) {
        return dao.findByIds(ids);
    }

    @Override
    public Map<K, V> map(List<K> ids) {
        Map<K, V> kvMap = this.list(ids).stream().collect(Collectors.toMap(
                v -> {
                    ReflectUtil.FieldNameValue nameValue = ReflectUtil.getFieldNameValue(v, Id.class);
                    Assert.notNull(nameValue, "nameValue not null");
                    return (K) nameValue.getFieldValue();
                },
                v -> v, (oldValue, newValue) -> newValue));
        LinkedHashMap<K,V> linkedHashMap = Maps.newLinkedHashMap();
        for (K id : ids) {
            linkedHashMap.put(id, kvMap.getOrDefault(id, null));
        }
        return linkedHashMap;
    }

    @Override
    public Map<K, V> map(K ... ids) {
        return this.map(Arrays.asList(ids));
    }

    @Override
    public boolean update(V v) {
        return dao.updateById(v).getModifiedCount() > 0;
    }

    @Override
    public boolean updateWithVersion(V v) {
        return dao.updateByIdWithVersion(v).getModifiedCount() > 0;
    }

    @Override
    public boolean delete(K id) {
        return dao.deleteById(id).getDeletedCount() > 0;
    }
}
