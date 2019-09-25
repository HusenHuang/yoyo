package com.yoyo.authority.menu.service;

import com.yoyo.authority.menu.pojo.dto.MenuDTO;
import com.yoyo.authority.menu.pojo.response.MenuShowResponse;
import com.yoyo.authority.menu.pojo.response.MenuListShowResponse;
import com.yoyo.framework.api.IRTService;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:24
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IMenuService extends IRTService<String, MenuDTO> {

    boolean addMenu(String name, String path, String parentId, int ordered);

    boolean updateMenu(String mid ,String name, String path, String parentId, int ordered);

    MenuShowResponse getMenu(String mid);

    boolean deleteMenu(String mid);

    MenuListShowResponse showAllMenu();
}
