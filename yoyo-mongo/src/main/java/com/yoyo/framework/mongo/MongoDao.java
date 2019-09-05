package com.yoyo.framework.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yoyo.framework.json.JSONUtils;
import com.yoyo.framework.reflect.ReflectUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
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

    private Class<K> kClass;

    private Class<V> vClass;

    /**
     * 写入泛型Class类型
     */
    private void writeClassType() {
        kClass = (Class<K>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        vClass = (Class<V>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * 新增
     * @param v
     * @return
     */
    public V insert(V v) {
        return mongoTemplate.insert(v);
    }

    /**
     * 查询
     * @param id
     * @return
     */
    public V findById(K id) {
        writeClassType();
        return mongoTemplate.findById(id, vClass);
    }

    /**
     * 强制更新
     * @param v
     * @return
     */
    public UpdateResult updateById(V v) {
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        Object id = ReflectUtil.getFieldValue(v, Id.class);
        return mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), update, v.getClass());
    }

    /**
     * 乐观锁更新
     * @param v
     * @return
     */
    public UpdateResult updateByIdWithVersion(V v) {
        Object id = ReflectUtil.getFieldValue(v, Id.class);
        ReflectUtil.FieldNameValue version = ReflectUtil.getFieldNameValue(v, MongoVersion.class);
        Assert.notNull(version, "MongoVersion not find");
        Object fieldValue = Optional.ofNullable(version.getFieldValue()).orElse("0");
        Criteria criteria = Criteria.where("_id").is(id).and(version.getFieldName()).is(fieldValue);
        // 版本号+1
        ReflectUtil.setFieldValue(v, version.getFieldName(), (Integer.parseInt(fieldValue.toString()) + 1) + "");
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        return mongoTemplate.updateFirst(Query.query(criteria), update, v.getClass());
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public DeleteResult deleteById(K id) {
        return mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)));
    }
}
