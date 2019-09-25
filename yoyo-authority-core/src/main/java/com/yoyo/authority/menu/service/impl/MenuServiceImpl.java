package com.yoyo.authority.menu.service.impl;

import com.yoyo.authority.menu.config.ConfigManager;
import com.yoyo.authority.menu.dao.MenuRepository;
import com.yoyo.authority.menu.manager.MenuManager;
import com.yoyo.authority.menu.pojo.dto.MenuDTO;
import com.yoyo.authority.menu.pojo.response.MenuShowResponse;
import com.yoyo.authority.menu.pojo.MenuVO;
import com.yoyo.authority.menu.pojo.response.MenuListShowResponse;
import com.yoyo.authority.menu.service.IMenuService;
import com.yoyo.framework.api.RTMongoServiceCacheImpl;
import com.yoyo.framework.common.SystemConstant;
import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.utils.BeanUtils;
import com.yoyo.framework.zookeeper.annotation.ZKDistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:25
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Service
public class MenuServiceImpl extends RTMongoServiceCacheImpl<String,MenuDTO> implements IMenuService {

    public MenuServiceImpl() {
        super(ConfigManager.MENU_REDIS_CONFIG_PRE, ConfigManager.MENU_REDIS_CONFIG_EXPIRE_SECOND);
    }


    @Override
    public boolean addMenu(String name, String path, String parentId, int ordered) {
        MenuDTO menuDTO = new MenuDTO()
                .setName(name)
                .setPath(path)
                .setParentId(StringUtils.hasLength(parentId) ? parentId : "ROOT")
                .setOrdered(ordered)
                .setMenuStatus(0);
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

    @ZKDistributedLock(value = "zkPath")
    @Override
    public MenuShowResponse getMenu(String mid) {
        MenuDTO menuDTO = this.get(mid);
        MenuVO menuVO = BeanUtils.copy(menuDTO, MenuVO.class);
        return new MenuShowResponse().setMenu(menuVO);
    }

    @Override
    public boolean deleteMenu(String mid) {
        return this.delete(mid);
    }

    @Override
    public MenuListShowResponse showAllMenu() {
        List<MenuDTO> menus = this.list();
        List<MenuVO> vos = menus.stream().map(s -> BeanUtils.copy(s, MenuVO.class)).collect(Collectors.toList());
        vos = MenuManager.loadTree(vos, SystemConstant.ROOT);
        return new MenuListShowResponse().setVos(vos);
    }
}
