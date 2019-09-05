package com.yoyo.authority.account.service;

import com.yoyo.authority.account.pojo.*;
import com.yoyo.framework.api.IRTService;
import com.yoyo.framework.api.IRTWithVersionService;

/***
 @Author:MrHuang
 @Date: 2019/9/4 17:29
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IAccountService extends IRTWithVersionService<String, AccountDTO> {

    AccountRegisterRsp register(AccountRegisterReq req);

    AccountLoginRsp login(AccountLoginReq req);
}
