package com.yoyo.authority.account.api;

import com.yoyo.authority.account.pojo.request.AccountBindRoleRequest;
import com.yoyo.authority.account.pojo.request.AccountLoginRequest;
import com.yoyo.authority.account.pojo.request.AccountRegisterRequest;
import com.yoyo.authority.account.pojo.response.AccountBindRoleResponse;
import com.yoyo.authority.account.pojo.response.AccountLoginResponse;
import com.yoyo.authority.account.pojo.response.AccountRegisterResponse;
import com.yoyo.authority.account.service.IAccountApiService;
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
    private IAccountApiService accountApiService;

    @RequestMapping
    public String index() {
        return "h1";
    }

    @PostMapping("/register")
    public RTRaw<AccountRegisterResponse> register(@Validated @RequestBody AccountRegisterRequest req) {
        return RTRawWrite.success(accountApiService.register(req));
    }

    @PostMapping("/login")
    public RTRaw<AccountLoginResponse> login(@RequestBody AccountLoginRequest req) {
        return RTRawWrite.success(accountApiService.login(req));
    }

    @PostMapping("/bindRole")
    public RTRaw<AccountBindRoleResponse> bindRole(@RequestBody AccountBindRoleRequest req) {
        return RTRawWrite.success(accountApiService.bindRole(req));
    }
}
