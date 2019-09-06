package com.yoyo.authority.role.service.impl;

import com.yoyo.authority.role.dao.RoleDao;
import com.yoyo.authority.role.pojo.RoleDTO;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.exception.RTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/5 16:56
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Repository
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDTO add(RoleDTO roleDTO) {
        return roleDao.insert(roleDTO);
    }

    @Override
    public RoleDTO get(String id) {
        return roleDao.findById(id);
    }

    @Override
    public boolean update(RoleDTO roleDTO) {
        return roleDao.updateById(roleDTO).getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String id) {
        return roleDao.deleteById(id).getDeletedCount() > 0;
    }

    @Override
    public boolean updateWithVersion(RoleDTO roleDTO) {
        return roleDao.updateByIdWithVersion(roleDTO).getModifiedCount() > 0;
    }

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
}
