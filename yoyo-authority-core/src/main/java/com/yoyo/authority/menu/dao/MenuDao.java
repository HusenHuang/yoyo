package com.yoyo.authority.menu.dao;

import com.yoyo.authority.menu.pojo.MenuDTO;
import com.yoyo.framework.mongo.MongoDao;
import org.springframework.stereotype.Repository;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:24
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class MenuDao extends MongoDao<String, MenuDTO> {

}
