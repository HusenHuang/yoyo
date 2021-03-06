package com.yoyo.framework.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yoyo.framework.api.RTPaging;
import com.yoyo.framework.json.JSONUtils;
import com.yoyo.framework.mongo.annotation.MongoVersion;
import com.yoyo.framework.reflect.ReflectUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/***
 @Author:MrHuang
 @Date: 2019/9/4 16:19
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class MongoRepository<K,V> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperation mongoOperation;

    /**
     * 新增
     * @param v 新增对象
     * @return
     */
    public V insert(V v) {
        mongoOperation.builderCreateTime(v);
        mongoOperation.builderUpdateTime(v);
        return mongoTemplate.insert(v);
    }

    /**
     * 查询所有
     * @return
     */
    public List<V> findAll() {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.findAll(vClass);
    }

    /**
     * 查询
     * @param id 主键ID
     * @return
     */
    public V findById(K id) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.findById(id, vClass);
    }

    /**
     * 根据ID批量查询
     * @param ids 主键ID集合
     * @return
     */
    public List<V> findByIds(List<K> ids) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        String idFieldName = ReflectUtil.getFieldName(vClass, Id.class);
        Assert.notNull(idFieldName, "@Id not find");
        return mongoTemplate.find(Query.query(Criteria.where(idFieldName).in(ids)), vClass);
    }

    /**
     * 强制更新
     * @param v 更新对象
     * @return
     */
    public UpdateResult updateById(V v) {
        ReflectUtil.FieldNameValue id = ReflectUtil.getFieldNameValue(v, Id.class);
        Assert.notNull(id, "@Id not find");
        // 设置更新时间
        mongoOperation.builderUpdateTime(v);
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        update.set(MongoConstant.CLASS, v.getClass().getName());
        return mongoTemplate.updateFirst(Query.query(Criteria.where(id.getFieldName()).is(id.getFieldValue())), update, v.getClass());
    }

    /**
     * 乐观锁更新
     * @param v 更新对象
     * @return
     */
    public UpdateResult updateByIdWithVersion(V v) {
        ReflectUtil.FieldNameValue id = ReflectUtil.getFieldNameValue(v, Id.class);
        Assert.notNull(id, "@Id not find");
        ReflectUtil.FieldNameValue version = ReflectUtil.getFieldNameValue(v, MongoVersion.class);
        Assert.notNull(version, "@MongoVersion not find");
        Object fieldValue = Optional.ofNullable(version.getFieldValue()).orElse(0);
        // 版本号+1
        ReflectUtil.setFieldValue(v, version.getFieldName(), (int)fieldValue + 1);
        // 设置更新时间
        mongoOperation.builderUpdateTime(v);
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        update.set(MongoConstant.CLASS, v.getClass().getName());
        Criteria criteria = Criteria.where(id.getFieldName()).is(id.getFieldValue()).and(version.getFieldName()).is(fieldValue);
        return mongoTemplate.updateFirst(Query.query(criteria), update, v.getClass());
    }

    /**
     * 物理删除
     * @param id 主键ID
     * @return
     */
    public DeleteResult deleteById(K id) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        String idFieldName = ReflectUtil.getFieldName(vClass, Id.class);
        Assert.notNull(idFieldName, "@Id not find");
        return mongoTemplate.remove(Query.query(Criteria.where(idFieldName).is(id)), vClass);
    }


    /**
     * 根据查询条件查找列表
     * @param criteria 查询条件
     * @return
     */
    public List<V> find(Criteria criteria) {
        return this.find(criteria, null, null, null);
    }

    /**
     * 根据查询条件查找列表
     * @param criteria 查询条件
     * @param sort 排序条件
     * @return
     */
    public List<V> find(Criteria criteria, Sort sort) {
        return this.find(criteria, sort, null, null);
    }

    /**
     * 根据查询条件查找列表
     * @param criteria 查询条件
     * @param sort 排序条件
     * @param skip 分页跳过条数
     * @param limit 分页限制条数
     * @return
     */
    public List<V> find(Criteria criteria, Sort sort, Long skip, Integer limit) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Query query = Query.query(criteria);
        if (Objects.nonNull(sort)) {
            query.with(sort);
        }
        if (Objects.nonNull(skip)) {
            query.skip(skip);
        }
        if (Objects.nonNull(limit)) {
            query.limit(limit);
        }
        return mongoTemplate.find(query, vClass);
    }

    /**
     * 根据查询条件查找条数
     * @param criteria 查询条件
     * @return
     */
    public long count(Criteria criteria) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.count(Query.query(criteria), vClass);
    }


    /**
     * 根据查询条件分页查询
     * @param criteria 查询条件
     * @param sort 排序条件
     * @param pageNow 查找的页数
     * @param pageSize 每页显示大小
     */
    public RTPaging<V> paging(Criteria criteria, Sort sort, long pageNow, int pageSize) {
        long totalRecord = this.count(criteria);
        long totalPage = RTPaging.getTotalPage(totalRecord, pageSize);
        long skip = RTPaging.getSkip(pageNow, pageSize);
        List<V> recond = this.find(criteria, sort, skip, pageSize);
        return new RTPaging<V>().setPageNow(pageNow).setPageSize(pageSize)
                .setTotalRecord(totalRecord).setTotalPage(totalPage)
                .setRecord(recond);
    }


    /**
     * 更新第一条匹配到的
     * @param criteria 查询条件
     * @param update 修改
     * @return
     */
    public UpdateResult updateFirst(Criteria criteria, Update update) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.updateFirst(Query.query(criteria), update, vClass);
    }

    /**
     * 更新所有匹配到的
     * @param criteria 查询条件
     * @param update 修改
     * @return
     */
    public UpdateResult updateMulti(Criteria criteria, Update update) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.updateMulti(Query.query(criteria), update, vClass);
    }
}
