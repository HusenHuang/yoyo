package com.yoyo.authority.account.service;

import com.yoyo.authority.account.pojo.dto.AccountDTO;
import com.yoyo.authority.account.pojo.request.AccountBindRoleRequest;
import com.yoyo.authority.account.pojo.request.AccountLoginRequest;
import com.yoyo.authority.account.pojo.request.AccountRegisterRequest;
import com.yoyo.authority.account.pojo.response.AccountBindRoleResponse;
import com.yoyo.authority.account.pojo.response.AccountLoginResponse;
import com.yoyo.authority.account.pojo.response.AccountRegisterResponse;
import com.yoyo.framework.api.IRTService;

/***
 @Author:MrHuang
 @Date: 2019/9/4 17:29
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IAccountService extends IRTService<String, AccountDTO> {

    AccountRegisterResponse register(AccountRegisterRequest req);

    AccountLoginResponse login(AccountLoginRequest req);

    AccountBindRoleResponse bindRole(AccountBindRoleRequest req);
}
