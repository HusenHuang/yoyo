package com.yoyo.authority.account.service.impl;

import com.yoyo.authority.account.config.ConfigManager;
import com.yoyo.authority.account.dao.AccountRepository;
import com.yoyo.authority.account.pojo.dto.AccountDTO;
import com.yoyo.authority.account.pojo.request.AccountBindRoleRequest;
import com.yoyo.authority.account.pojo.request.AccountLoginRequest;
import com.yoyo.authority.account.pojo.request.AccountRegisterRequest;
import com.yoyo.authority.account.pojo.response.AccountBindRoleResponse;
import com.yoyo.authority.account.pojo.response.AccountLoginResponse;
import com.yoyo.authority.account.pojo.response.AccountRegisterResponse;
import com.yoyo.authority.account.service.IAccountService;
import com.yoyo.authority.role.pojo.resposne.RoleMenuGetResponse;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTMongoServiceCacheImpl;
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
public class AccountServiceImpl extends RTMongoServiceCacheImpl<String, AccountDTO> implements IAccountService  {

    public AccountServiceImpl() {
        super(ConfigManager.ACCOUNT_REDIS_CONFIG_PRE, ConfigManager.ACCOUNT_REDIS_CONFIG_EXPIRE_SECOND);
    }

    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private IRoleService roleService;

    @Override
    public AccountRegisterResponse register(AccountRegisterRequest req) {
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
                .setUpdateTime(DateUtils.localDateTime2TimeString(LocalDateTime.now()));
        AccountDTO result = this.add(accountDTO);
        return BeanUtils.copy(result, AccountRegisterResponse.class);
    }

    @Override
    public AccountLoginResponse login(AccountLoginRequest req) {
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
        AccountLoginResponse rsp = BeanUtils.copy(accountDTO, AccountLoginResponse.class);
        rsp.setTokenId(JwtUtils.encode(accountDTO.getAid()));
        if (StringUtils.hasLength(accountDTO.getBindRoleId())) {
            RoleMenuGetResponse roleMenu = roleService.getRoleMenuTree(accountDTO.getBindRoleId());
            rsp.setMenuList(roleMenu.getMenuList());
        }
        return rsp;
    }

    @Override
    public AccountBindRoleResponse bindRole(AccountBindRoleRequest req) {
        String aid = JwtUtils.decode(req.getTokenId());
        AccountDTO accountDTO = this.get(aid);
        if (accountDTO == null) {
            throw new RTException("账号不存在");
        }
        accountDTO.setBindRoleId(req.getRid());
        boolean result = this.updateWithVersion(accountDTO);
        return new AccountBindRoleResponse().setOpStatus(result);
    }


}
