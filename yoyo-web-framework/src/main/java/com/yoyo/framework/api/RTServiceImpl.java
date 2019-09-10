package com.yoyo.framework.api;

import com.yoyo.framework.mongo.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/9 11:28
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class RTServiceImpl<K,V> implements IRTService<K,V> {

    @Autowired
    private MongoDao<K,V> dao;

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
        return dao.findByIds(ids);
    }

    @Override
    public List<V> list(Collection<K> ids) {
        return dao.findByIds(ids);
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
