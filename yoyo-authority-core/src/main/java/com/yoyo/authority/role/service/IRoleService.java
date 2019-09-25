package com.yoyo.authority.role.service;

import com.yoyo.authority.role.pojo.dto.RoleDTO;
import com.yoyo.authority.role.pojo.resposne.RoleMenuGetResponse;
import com.yoyo.framework.api.IRTService;

import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:55
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IRoleService extends IRTService<String, RoleDTO> {

    boolean addRole(String name, String remark);

    boolean updateRole(String id ,String name, String remark);

    boolean bindMenu(String id, List<String> menuIdList);

    RoleMenuGetResponse getRoleMenuTree(String rid);
}
