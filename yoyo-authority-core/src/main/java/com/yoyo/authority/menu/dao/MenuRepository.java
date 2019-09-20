package com.yoyo.authority.menu.dao;

import com.yoyo.authority.menu.pojo.dto.MenuDTO;
import com.yoyo.framework.mongo.MongoRepository;
import org.springframework.stereotype.Repository;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:24
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class MenuRepository extends MongoRepository<String, MenuDTO> {

}
