package com.yoyo.authority.menu.service;

import com.yoyo.authority.menu.pojo.MenuDTO;
import com.yoyo.authority.menu.pojo.MenuGetRsp;
import com.yoyo.framework.api.IRTWithVersionService;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:24
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IMenuService extends IRTWithVersionService<String, MenuDTO> {

    boolean addMenu(String name, String path, String parentId, int ordered);

    boolean updateMenu(String mid ,String name, String path, String parentId, int ordered);

    MenuGetRsp getMenu(String mid);

    boolean deleteMenu(String mid);
}
