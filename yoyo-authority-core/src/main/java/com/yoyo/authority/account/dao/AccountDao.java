package com.yoyo.authority.account.dao;

import com.yoyo.authority.account.pojo.AccountDTO;
import com.yoyo.framework.mongo.MongoDao;
import org.springframework.stereotype.Repository;

/***
 @Author:MrHuang
 @Date: 2019/9/4 14:34
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class AccountDao extends MongoDao<Integer, AccountDTO> {


}
