package com.yoyo.framework.mongo;

import com.yoyo.framework.mongo.annotation.MongoAutoKey;
import com.yoyo.framework.reflect.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Objects;

/***
 @Author:MrHuang
 @Date: 2019/9/20 19:58
 @DESC: TODO 调用Mongo Save方法时候会触发
 @VERSION: 1.0
 ***/
@Component
@Slf4j
public class MongoAutoKeyEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        log.info("onBeforeConvert");
        Object source = event.getSource();
        Field field = ReflectUtil.getField(source.getClass(), MongoAutoKey.class);
        if (Objects.nonNull(field)) {
            Class<?> type = field.getType();
            if (type != Long.class) {
                throw new IllegalArgumentException("@MongoAutoKey标记的字段类型只能是Long或者long");
            }
            Long nextId = this.getNextId(event.getCollectionName());
            ReflectUtil.setFieldValue(source, field.getName(), nextId);
        }
        super.onBeforeConvert(event);
    }

    private Long getNextId(String collName) {
        Query query = Query.query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        MongoAutoKeyTable seq = mongoTemplate.findAndModify(query, update, options, MongoAutoKeyTable.class);
        Assert.notNull(seq, "seq not null ...");
        return seq.getSeqId();
    }
}
