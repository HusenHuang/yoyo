package com.yoyo.authority.role.dao;

import com.yoyo.authority.role.pojo.dto.RoleDTO;
import com.yoyo.framework.mongo.MongoRepository;
import org.springframework.stereotype.Repository;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:54
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class RoleRepository extends MongoRepository<String, RoleDTO> {
}
