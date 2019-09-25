package com.yoyo.authority.account.service.impl;

import com.yoyo.authority.account.pojo.dto.AccountDTO;
import com.yoyo.authority.account.pojo.request.AccountBindRoleRequest;
import com.yoyo.authority.account.pojo.request.AccountLoginRequest;
import com.yoyo.authority.account.pojo.request.AccountRegisterRequest;
import com.yoyo.authority.account.pojo.response.AccountBindRoleResponse;
import com.yoyo.authority.account.pojo.response.AccountLoginResponse;
import com.yoyo.authority.account.pojo.response.AccountRegisterResponse;
import com.yoyo.authority.account.service.IAccountApiService;
import com.yoyo.authority.account.service.IAccountService;
import com.yoyo.authority.role.pojo.resposne.RoleMenuGetResponse;
import com.yoyo.authority.role.service.IRoleApiService;
import com.yoyo.framework.auth.JwtUtils;
import com.yoyo.framework.date.DateUtils;
import com.yoyo.framework.exception.RTException;
import com.yoyo.framework.utils.BeanUtils;
import com.yoyo.framework.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/***
 @Author:MrHuang
 @Date: 2019/9/25 14:41
 @DESC: TODO
 @VERSION: 1.0
 ***/
@org.apache.dubbo.config.annotation.Service
@org.springframework.stereotype.Service
public class AccountApiServiceImpl implements IAccountApiService {

    @Autowired
    private IRoleApiService roleApiService;

    @Autowired
    private IAccountService accountService;

    @Override
    public AccountRegisterResponse register(AccountRegisterRequest req) {
        if (!CheckUtils.isEmail(req.getEmail())) {
            throw new RTException("邮箱格式不对");
        }
        if (accountService.findByCriteria(req.getName(), null, null) != null) {
            throw new RTException("用户名已存在");
        }
        if (accountService.findByCriteria(null, req.getEmail(), null) != null) {
            throw new RTException("邮箱已存在");
        }
        AccountDTO accountDTO = new AccountDTO().setActiveState(0)
                .setName(req.getName())
                .setPassword(req.getPassword())
                .setEmail(req.getEmail())
                .setCreateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()))
                .setUpdateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()));
        AccountDTO result = accountService.add(accountDTO);
        return BeanUtils.copy(result, AccountRegisterResponse.class);
    }

    @Override
    public AccountLoginResponse login(AccountLoginRequest req) {
        AccountDTO accountDTO = null;
        if (StringUtils.hasLength(req.getName())) {
            accountDTO = accountService.findByCriteria(req.getName(), null, req.getPassword());
        } else if (StringUtils.hasLength(req.getEmail())) {
            if (!CheckUtils.isEmail(req.getEmail())) {
                throw new RTException("邮箱格式不对");
            }
            accountDTO = accountService.findByCriteria(null , req.getEmail(), req.getPassword());
        }
        if (accountDTO == null) {
            throw new RTException("登录名或者密码错误");
        }
        AccountLoginResponse rsp = BeanUtils.copy(accountDTO, AccountLoginResponse.class);
        rsp.setTokenId(JwtUtils.encode(accountDTO.getAid()));
        if (StringUtils.hasLength(accountDTO.getBindRoleId())) {
            RoleMenuGetResponse roleMenu = roleApiService.getRoleMenuTree(accountDTO.getBindRoleId());
            rsp.setMenuList(roleMenu.getMenuList());
        }
        return rsp;
    }

    @Override
    public AccountBindRoleResponse bindRole(AccountBindRoleRequest req) {
        String aid = JwtUtils.decode(req.getTokenId());
        AccountDTO accountDTO = accountService.get(aid);
        if (accountDTO == null) {
            throw new RTException("账号不存在");
        }
        accountDTO.setBindRoleId(req.getRid());
        boolean result = accountService.updateWithVersion(accountDTO);
        return new AccountBindRoleResponse().setOpStatus(result);
    }
}
