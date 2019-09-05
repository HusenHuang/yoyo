package com.yoyo.framework.api;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:59
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IRTWithVersionService<K,V> extends IRTService<K,V> {

    boolean updateWithVersion(V v);
}
