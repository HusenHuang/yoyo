package com.yoyo.authority.account.pojo.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/6 10:47
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class AccountBindRoleRsp implements Serializable {

    private boolean opStatus;
}
