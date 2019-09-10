package com.yoyo.authority.menu.service.impl;

import com.yoyo.authority.menu.config.ConfigManager;
import com.yoyo.authority.menu.dao.MenuDao;
import com.yoyo.authority.menu.pojo.MenuDTO;
import com.yoyo.authority.menu.pojo.MenuGetRsp;
import com.yoyo.authority.menu.pojo.MenuVO;
import com.yoyo.authority.menu.service.IMenuService;
import com.yoyo.framework.api.RTServiceCacheImpl;
import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:25
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Service
public class MenuServiceImpl extends RTServiceCacheImpl<String,MenuDTO> implements IMenuService {

    public MenuServiceImpl() {
        super(ConfigManager.MENU_REDIS_CONFIG_PRE, ConfigManager.MENU_REDIS_CONFIG_EXPIRE_SECOND);
    }

    @Autowired
    private MenuDao menuDao;

    @Override
    public boolean addMenu(String name, String path, String parentId, int ordered) {
        MenuDTO menuDTO = new MenuDTO()
                .setName(name)
                .setPath(path)
                .setParentId(StringUtils.hasLength(parentId) ? parentId : null)
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
        menuDTO.setName(name).setPath(path).setParentId(StringUtils.hasLength(parentId) ? parentId : null).setOrdered(ordered);
        return this.updateWithVersion(menuDTO);
    }

    @Override
    public MenuGetRsp getMenu(String mid) {
        MenuDTO menuDTO = this.get(mid);
        MenuVO menuVO = BeanUtils.copy(menuDTO, MenuVO.class);
        return new MenuGetRsp().setMenu(menuVO);
    }

    @Override
    public boolean deleteMenu(String mid) {
        return this.delete(mid);
    }
}
