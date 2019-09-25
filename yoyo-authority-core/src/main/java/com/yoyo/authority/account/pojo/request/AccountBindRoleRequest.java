package com.yoyo.authority.account.pojo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/6 10:46
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class AccountBindRoleRequest implements Serializable {

    private String tokenId;

    private String rid;
}
