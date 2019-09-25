package com.yoyo.authority.role.service.impl;

import com.yoyo.authority.role.config.ConfigManager;
import com.yoyo.authority.role.pojo.dto.RoleDTO;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTMongoServiceCacheImpl;
import org.springframework.stereotype.Service;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:56
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Service
public class RoleServiceImpl extends RTMongoServiceCacheImpl<String, RoleDTO> implements IRoleService {

    public RoleServiceImpl() {
        super(ConfigManager.ROLE_REDIS_CONFIG_PRE, ConfigManager.ROLE_REDIS_CONFIG_EXPIRE_SECOND);
    }
}
