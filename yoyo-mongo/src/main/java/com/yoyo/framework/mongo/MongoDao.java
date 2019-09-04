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

import java.lang.reflect.ParameterizedType;

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
     * 更新
     * @param v
     * @return
     */
    public UpdateResult updateById(V v) {
        Update update = Update.fromDocument(Document.parse(JSONUtils.object2Json(v)));
        Object fieldValue = ReflectUtil.getFieldValue(v, Id.class);
        return mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(fieldValue)), update, v.getClass());
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
