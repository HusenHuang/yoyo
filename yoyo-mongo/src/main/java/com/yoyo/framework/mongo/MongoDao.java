package com.yoyo.framework.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yoyo.framework.api.RTPaging;
import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.json.JSONUtils;
import com.yoyo.framework.mongo.annotation.MongoCreateTime;
import com.yoyo.framework.mongo.annotation.MongoUpdateTime;
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
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
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
public class MongoDao<K,V> {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     * @param v
     * @return
     */
    public V insert(V v) {
        this.builderCreateTime(v);
        this.builderUpdateTime(v);
        return mongoTemplate.insert(v);
    }

    /**
     * 查询
     * @param id
     * @return
     */
    public V findById(K id) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.findById(id, vClass);
    }

    /**
     * 根据ID批量查询
     * @param ids
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
     * @param v
     * @return
     */
    public UpdateResult updateById(V v) {
        ReflectUtil.FieldNameValue id = ReflectUtil.getFieldNameValue(v, Id.class);
        Assert.notNull(id, "@Id not find");
        // 设置更新时间
        builderUpdateTime(v);
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        update.set("_class", v.getClass().getName());
        return mongoTemplate.updateFirst(Query.query(Criteria.where(id.getFieldName()).is(id.getFieldValue())), update, v.getClass());
    }

    /**
     * 乐观锁更新
     * @param v
     * @return
     */
    public UpdateResult updateByIdWithVersion(V v) {
        ReflectUtil.FieldNameValue id = ReflectUtil.getFieldNameValue(v, Id.class);
        Assert.notNull(id, "@Id not find");
        ReflectUtil.FieldNameValue version = ReflectUtil.getFieldNameValue(v, MongoVersion.class);
        Assert.notNull(version, "@MongoVersion not find");
        Object fieldValue = Optional.ofNullable(version.getFieldValue()).orElse(0);
        Criteria criteria = Criteria.where(id.getFieldName()).is(id.getFieldValue()).and(version.getFieldName()).is(fieldValue);
        // 版本号+1
        ReflectUtil.setFieldValue(v, version.getFieldName(), (int)fieldValue + 1);
        // 设置更新时间
        builderUpdateTime(v);
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        update.set("_class", v.getClass().getName());
        return mongoTemplate.updateFirst(Query.query(criteria), update, v.getClass());
    }

    /**
     * 物理删除
     * @param id
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
     * @param criteria
     * @return
     */
    public List<V> find(Criteria criteria) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.find(Query.query(criteria), vClass);
    }

    /**
     * 根据查询条件查找列表
     * @param criteria
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
     * @param criteria
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
     * @param criteria
     * @param update
     * @return
     */
    public UpdateResult updateFirst(Criteria criteria, Update update) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.updateFirst(Query.query(criteria), update, vClass);
    }

    /**
     * 更新所有匹配到的
     * @param criteria
     * @param update
     * @return
     */
    public UpdateResult updateMulti(Criteria criteria, Update update) {
        Class<V> vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return mongoTemplate.updateMulti(Query.query(criteria), update, vClass);
    }

    private void builderCreateTime(V v) {
        Field field = ReflectUtil.getField(v.getClass(), MongoCreateTime.class);
        builderTime(field, v);
    }

    private void builderUpdateTime(V v) {
        Field field = ReflectUtil.getField(v.getClass(), MongoUpdateTime.class);
        builderTime(field, v);
    }

    private void builderTime(Field field, V v) {
        if (Objects.nonNull(field)) {
            Class<?> type = field.getType();
            if (type == String.class) {
                ReflectUtil.setFieldValue(v, field.getName(), DateUtils.localDateTime2TimeString(LocalDateTime.now()));
            } else if (type == LocalDateTime.class) {
                ReflectUtil.setFieldValue(v, field.getName(), LocalDateTime.now());
            }
        }
    }
}
