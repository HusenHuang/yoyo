package com.yoyo.authority.menu.service;

import com.yoyo.authority.menu.pojo.response.MenuListShowResponse;
import com.yoyo.authority.menu.pojo.response.MenuShowResponse;

/***
 @Author:MrHuang
 @Date: 2019/9/25 14:29
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IMenuApiService {

    boolean addMenu(String name, String path, String parentId, int ordered);

    boolean updateMenu(String mid ,String name, String path, String parentId, int ordered);

    MenuShowResponse getMenu(String mid);

    boolean deleteMenu(String mid);

    MenuListShowResponse showAllMenu();
}
