package com.yoyo.authority.role.service.impl;

import com.yoyo.authority.menu.pojo.MenuDTO;
import com.yoyo.authority.menu.pojo.MenuVO;
import com.yoyo.authority.menu.service.IMenuService;
import com.yoyo.authority.role.dao.RoleDao;
import com.yoyo.authority.role.pojo.RoleDTO;
import com.yoyo.authority.role.pojo.RoleMenuGetRsp;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTServiceImpl;
import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:56
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class RoleServiceImpl extends RTServiceImpl<String, RoleDTO> implements IRoleService {

    @Autowired
    private IMenuService menuService;

    @Override
    public boolean addRole(String name, String remark) {
        RoleDTO roleDTO = new RoleDTO().setName(name).setRemark(remark).setRoleStatus(0)
                .setCreateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()))
                .setUpdateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()))
                .setVersion("0");
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
    public RoleMenuGetRsp getRoleMenu(String rid) {
        RoleDTO roleDTO = this.get(rid);
        if (roleDTO == null) {
            throw new RTException("角色不存在");
        }
        String[] menuIds = roleDTO.getBindMenuId().toArray(new String[]{});
        List<MenuDTO> menus = menuService.list(menuIds);
        List<MenuVO> menuVOList = menus.stream().map(s -> BeanUtils.copy(s, MenuVO.class)).collect(Collectors.toList());
        return new RoleMenuGetRsp().setMenuList(menuVOList);
    }
}
