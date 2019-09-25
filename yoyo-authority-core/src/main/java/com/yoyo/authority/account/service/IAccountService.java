package com.yoyo.authority.account.service;

import com.yoyo.authority.account.pojo.dto.AccountDTO;
import com.yoyo.authority.account.pojo.request.AccountBindRoleReq;
import com.yoyo.authority.account.pojo.request.AccountLoginReq;
import com.yoyo.authority.account.pojo.request.AccountRegisterReq;
import com.yoyo.authority.account.pojo.response.AccountBindRoleRsp;
import com.yoyo.authority.account.pojo.response.AccountLoginRsp;
import com.yoyo.authority.account.pojo.response.AccountRegisterRsp;
import com.yoyo.framework.api.IRTService;

/***
 @Author:MrHuang
 @Date: 2019/9/4 17:29
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IAccountService {

    AccountRegisterRsp register(AccountRegisterReq req);

    AccountLoginRsp login(AccountLoginReq req);

    AccountBindRoleRsp bindRole(AccountBindRoleReq req);
}
