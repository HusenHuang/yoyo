package com.yoyo.authority.role.service;

import com.yoyo.authority.role.pojo.resposne.RoleMenuGetResponse;

import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/25 14:35
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IRoleApiService {

    boolean addRole(String name, String remark);

    boolean updateRole(String id ,String name, String remark);

    boolean bindMenu(String id, List<String> menuIdList);

    RoleMenuGetResponse getRoleMenuTree(String rid);
}
