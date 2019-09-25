package com.yoyo.authority.role.service.impl;

import com.yoyo.authority.menu.manager.MenuManager;
import com.yoyo.authority.menu.pojo.MenuVO;
import com.yoyo.authority.menu.pojo.dto.MenuDTO;
import com.yoyo.authority.menu.service.IMenuService;
import com.yoyo.authority.role.config.ConfigManager;
import com.yoyo.authority.role.pojo.dto.RoleDTO;
import com.yoyo.authority.role.pojo.resposne.RoleMenuGetResponse;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTMongoServiceCacheImpl;
import com.yoyo.framework.common.SystemConstant;
import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:56
 @DESC: TODO
 @VERSION: 1.0
 ***/
@org.springframework.stereotype.Service
@org.apache.dubbo.config.annotation.Service
public class RoleServiceImpl extends RTMongoServiceCacheImpl<String, RoleDTO> implements IRoleService {

    public RoleServiceImpl() {
        super(ConfigManager.ROLE_REDIS_CONFIG_PRE, ConfigManager.ROLE_REDIS_CONFIG_EXPIRE_SECOND);
    }

    @Autowired
    private IMenuService menuService;

    @Override
    public boolean addRole(String name, String remark) {
        RoleDTO roleDTO = new RoleDTO().setName(name).setRemark(remark).setRoleStatus(0)
                .setCreateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()))
                .setUpdateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()));
        RoleDTO result = this.add(roleDTO);
        return result != null;
    }

    @Override
    public boolean updateRole(String id, String name, String remark) {
        RoleDTO roleDTO = this.get(id);
        if (roleDTO == null) {
            throw new RTException("角色不存在");
        }
        roleDTO.setName(name);
        roleDTO.setRemark(remark);
        return this.updateWithVersion(roleDTO);
    }

    @Override
    public boolean bindMenu(String id, List<String> menuIdList) {
        RoleDTO roleDTO = this.get(id);
        if (roleDTO == null) {
            throw new RTException("角色不存在");
        }
        roleDTO.setBindMenuId(menuIdList);
        return this.updateWithVersion(roleDTO);
    }

    @Override
    public RoleMenuGetResponse getRoleMenuTree(String rid) {
        RoleDTO roleDTO = this.get(rid);
        if (roleDTO == null) {
            throw new RTException("角色不存在");
        }
        if (!CollectionUtils.isEmpty(roleDTO.getBindMenuId())) {
            String[] menuIds = roleDTO.getBindMenuId().toArray(new String[]{});
            List<MenuDTO> menus = menuService.list(menuIds);
            List<MenuVO> menuVOS = menus.stream().map(s -> BeanUtils.copy(s, MenuVO.class)).collect(Collectors.toList());
            List<MenuVO> loadTree = MenuManager.loadTree(menuVOS, SystemConstant.ROOT);
            return new RoleMenuGetResponse().setMenuList(loadTree);
        } else {
            return new RoleMenuGetResponse().setMenuList(new ArrayList<>());
        }
    }


}
