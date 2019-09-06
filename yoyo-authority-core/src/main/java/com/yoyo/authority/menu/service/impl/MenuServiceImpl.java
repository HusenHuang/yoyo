package com.yoyo.authority.menu.service.impl;

import com.yoyo.authority.menu.dao.MenuDao;
import com.yoyo.authority.menu.pojo.MenuDTO;
import com.yoyo.authority.menu.service.IMenuService;
import com.yoyo.framework.exception.RTException;
import org.springframework.beans.factory.annotation.Autowired;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:25
 @DESC: TODO
 @VERSION: 1.0
 ***/
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuDao menuDao;


    @Override
    public boolean updateWithVersion(MenuDTO menuDTO) {
        return menuDao.updateByIdWithVersion(menuDTO).getModifiedCount() > 0;
    }

    @Override
    public MenuDTO add(MenuDTO menuDTO) {
        return menuDao.insert(menuDTO);
    }

    @Override
    public MenuDTO get(String id) {
        return menuDao.findById(id);
    }

    @Override
    public boolean update(MenuDTO menuDTO) {
        return menuDao.updateById(menuDTO).getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String id) {
        return menuDao.deleteById(id).getDeletedCount() > 0;
    }

    @Override
    public boolean addMenu(String name, String path, String parentId, int ordered) {
        MenuDTO menuDTO = new MenuDTO()
                .setName(name)
                .setPath(path)
                .setParentId(parentId)
                .setOrdered(ordered)
                .setMenuStatus(0)
                .setVersion("0");
        return this.add(menuDTO) != null;
    }

    @Override
    public boolean updateMenu(String mid, String name, String path, String parentId, int ordered) {
        MenuDTO menuDTO = this.get(mid);
        if (menuDTO == null) {
            throw new RTException("菜单不存在");
        }
        menuDTO.setName(name).setPath(path).setParentId(parentId).setOrdered(ordered);
        return this.updateWithVersion(menuDTO);
    }

    @Override
    public MenuDTO getMenu(String mid) {
        return this.get(mid);
    }
}
