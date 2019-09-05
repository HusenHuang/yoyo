package com.yoyo.framework.api;

/***
 @Author:MrHuang
 @Date: 2019/9/5 10:57
 @DESC: TODO 业务CRUD基础Service
 @VERSION: 1.0
 ***/
public interface IRTService<K, V> {

    V add(V accountDTO);

    V get(K id);

    boolean update(V accountDTO);

    boolean delete(K id);
}
