package com.yoyo.authority.role.dao;

import com.yoyo.authority.role.pojo.RoleDTO;
import com.yoyo.framework.mongo.MongoDao;
import org.springframework.stereotype.Repository;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:54
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class RoleDao extends MongoDao<String, RoleDTO> {
}
