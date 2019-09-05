package com.yoyo.authority.account.dao;

import com.yoyo.authority.account.pojo.AccountDTO;
import com.yoyo.framework.mongo.MongoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/***
 @Author:MrHuang
 @Date: 2019/9/4 14:34
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class AccountDao extends MongoDao<String, AccountDTO> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public AccountDTO findByCriteria(String name, String email, String password) {
        Criteria criteria = new Criteria();
        if (StringUtils.hasLength(name)) {
            criteria.and("name").is(name);
        }
        if (StringUtils.hasLength(email)) {
            criteria.and("email").is(email);
        }
        if (StringUtils.hasLength(password)) {
            criteria.and("password").is(password);
        }
        return mongoTemplate.findOne(Query.query(criteria), AccountDTO.class);
    }


}
