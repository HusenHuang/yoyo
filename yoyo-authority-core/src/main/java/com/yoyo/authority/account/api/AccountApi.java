package com.yoyo.authority.account.api;

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

    @RequestMapping
    public String index() {
        return "h1";
    }
}
