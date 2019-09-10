package com.yoyo.framework.api;

import java.util.List;
import java.util.Map;

/***
 @Author:MrHuang
 @Date: 2019/9/5 10:57
 @DESC: TODO 业务CRUD基础Service
 @VERSION: 1.0
 ***/
public interface IRTService<K, V> {

    /**
     * 新增
     * @param v
     * @return
     */
    V add(V v);

    /**
     * 根据ID查询单个对象
     * @param id
     * @return
     */
    V get(K id);

    /**
     * 根据多个ID查询多个对象
     * 如果为空不返回
     * @param ids
     * @return
     */
    List<V> list(K ... ids);

    /**
     * 根据多个ID查询多个对象
     * 如果为空不返回
     * @param ids
     * @return
     */
    List<V> list(List<K> ids);


    /**
     * 根据多个ID查询多个对象
     * 如果为空,返回null
     * @param ids
     * @return
     */
    Map<K,V> map(List<K> ids);

    /**
     * 根据多个ID查询多个对象
     * 如果为空,返回null
     * @param ids
     * @return
     */
    Map<K,V> map(K ... ids);

    /**
     * 根据ID更新对象信息
     * @param v
     * @return
     */
    boolean update(V v);

    /**
     * 根据ID和版本号更新对象信息
     * TODO: 需要有@MongoVersion注解,字段类型必须为String
     * @param v
     * @return
     */
    boolean updateWithVersion(V v);

    /**
     * 物理删除
     * @param id
     * @return
     */
    boolean delete(K id);

}
