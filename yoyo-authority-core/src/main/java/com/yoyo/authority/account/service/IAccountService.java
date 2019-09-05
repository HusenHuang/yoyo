package com.yoyo.authority.account.service;

import com.yoyo.authority.account.pojo.AccountDTO;
import com.yoyo.authority.account.pojo.AccountRegisterReq;
import com.yoyo.authority.account.pojo.AccountRegisterRsp;
import com.yoyo.framework.api.IRTService;

/***
 @Author:MrHuang
 @Date: 2019/9/4 17:29
 @DESC: TODO
 @VERSION: 1.0
 ***/
public interface IAccountService extends IRTService<String, AccountDTO> {

    AccountRegisterRsp register(AccountRegisterReq req);

}
