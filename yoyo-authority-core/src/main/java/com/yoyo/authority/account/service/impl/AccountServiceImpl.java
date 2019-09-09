package com.yoyo.authority.account.service.impl;

import com.yoyo.authority.account.dao.AccountDao;
import com.yoyo.authority.account.pojo.*;
import com.yoyo.authority.account.service.IAccountService;
import com.yoyo.authority.role.pojo.RoleMenuGetRsp;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTServiceImpl;
import com.yoyo.framework.auth.JwtUtils;
import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.utils.BeanUtils;
import com.yoyo.framework.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/***
 @Author:MrHuang
 @Date: 2019/9/4 17:30
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Service
public class AccountServiceImpl extends RTServiceImpl<String, AccountDTO> implements IAccountService  {


    @Autowired
    private AccountDao accountDao;

    @Autowired
    private IRoleService roleService;

    @Override
    public AccountRegisterRsp register(AccountRegisterReq req) {
        if (!CheckUtils.isEmail(req.getEmail())) {
            throw new RTException("邮箱格式不对");
        }
        if (accountDao.findByCriteria(req.getName(), null, null) != null) {
            throw new RTException("用户名已存在");
        }
        if (accountDao.findByCriteria(null, req.getEmail(), null) != null) {
            throw new RTException("邮箱已存在");
        }
        AccountDTO accountDTO = new AccountDTO().setActiveState(0)
                .setName(req.getName())
                .setPassword(req.getPassword())
                .setEmail(req.getEmail())
                .setCreateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()))
                .setUpdateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()))
                .setVersion("0");
        AccountDTO result = this.add(accountDTO);
        return BeanUtils.copy(result, AccountRegisterRsp.class);
    }

    @Override
    public AccountLoginRsp login(AccountLoginReq req) {
        AccountDTO accountDTO = null;
        if (StringUtils.hasLength(req.getName())) {
            accountDTO = accountDao.findByCriteria(req.getName(), null, req.getPassword());
        } else if (StringUtils.hasLength(req.getEmail())) {
            if (!CheckUtils.isEmail(req.getEmail())) {
                throw new RTException("邮箱格式不对");
            }
            accountDTO = accountDao.findByCriteria(null , req.getEmail(), req.getPassword());
        }
        if (accountDTO == null) {
            throw new RTException("登录名或者密码错误");
        }
        AccountLoginRsp rsp = BeanUtils.copy(accountDTO, AccountLoginRsp.class);
        rsp.setTokenId(JwtUtils.encode(accountDTO.getAid()));
        if (StringUtils.hasLength(accountDTO.getBindRoleId())) {
            RoleMenuGetRsp roleMenu = roleService.getRoleMenu(accountDTO.getBindRoleId());
            rsp.setMenuList(roleMenu.getMenuList());
        }
        return rsp;
    }

    @Override
    public AccountBindRoleRsp bindRole(AccountBindRoleReq req) {
        String aid = JwtUtils.decode(req.getTokenId());
        AccountDTO accountDTO = this.get(aid);
        if (accountDTO == null) {
            throw new RTException("账号不存在");
        }
        accountDTO.setBindRoleId(req.getRid());
        boolean result = this.updateWithVersion(accountDTO);
        return new AccountBindRoleRsp().setOpStatus(result);
    }


}
