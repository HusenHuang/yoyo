package com.yoyo.framework.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/***
 @Author:MrHuang
 @Date: 2019/9/4 16:18
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Component
public class MongoClient {

    private static MongoTemplate mongoTemplate;

    @Autowired
    public MongoClient(MongoTemplate mongoTemplate) {
        MongoClient.mongoTemplate = mongoTemplate;
    }


    public static MongoTemplate template() {
        return MongoClient.mongoTemplate;
    }
}
