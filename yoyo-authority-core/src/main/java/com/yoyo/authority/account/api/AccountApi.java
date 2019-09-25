package com.yoyo.authority.account.api;

import com.yoyo.authority.account.pojo.request.AccountBindRoleRequest;
import com.yoyo.authority.account.pojo.request.AccountLoginRequest;
import com.yoyo.authority.account.pojo.request.AccountRegisterRequest;
import com.yoyo.authority.account.pojo.response.AccountBindRoleResponse;
import com.yoyo.authority.account.pojo.response.AccountRegisterResponse;
import com.yoyo.authority.account.service.IAccountService;
import com.yoyo.framework.api.RTRaw;
import com.yoyo.framework.api.RTRawWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 @Author:MrHuang
 @Date: 2019/9/4 18:33
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RestController
@RequestMapping("/account")
public class AccountApi {

    @Autowired
    private IAccountService accountService;

    @RequestMapping
    public String index() {
        return "h1";
    }

    @PostMapping("/register")
    public RTRaw<AccountRegisterResponse> register(@Validated @RequestBody AccountRegisterRequest req) {
        return RTRawWrite.success(accountService.register(req));
    }

    @PostMapping("/login")
    public RTRaw<Object> login(@RequestBody AccountLoginRequest req) {
        return RTRawWrite.success(accountService.login(req));
    }

    @PostMapping("/bindRole")
    public RTRaw<AccountBindRoleResponse> bindRole(@RequestBody AccountBindRoleRequest req) {
        return RTRawWrite.success(accountService.bindRole(req));
    }
}
