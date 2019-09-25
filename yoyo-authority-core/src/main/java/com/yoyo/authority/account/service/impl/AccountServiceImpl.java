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
    private AccountRepository accountRepository;


    @Override
    public AccountDTO findByCriteria(String name, String email, String password) {
        return accountRepository.findByCriteria(name, email, password);
    }
}
