package com.yoyo.authority.role.service;

import com.yoyo.authority.role.pojo.RoleDTO;
import com.yoyo.authority.role.pojo.RoleMenuGetRsp;

import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:55
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IRoleService extends com.yoyo.framework.api.IRTService<String, RoleDTO> {

    boolean addRole(String name, String remark);

    boolean updateRole(String id ,String name, String remark);

    boolean bindMenu(String id, List<String> menuIdList);

    RoleMenuGetRsp getRoleMenu(String rid);
}
