package com.yoyo.framework.api;

import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/5 10:57
 @DESC: TODO 业务CRUD基础Service
 @VERSION: 1.0
 ***/
public interface IRTService<K, V> {

    V add(V v);

    V get(K id);

    List<V> list(K ... ids);

    boolean update(V v);

    boolean delete(K id);
}
