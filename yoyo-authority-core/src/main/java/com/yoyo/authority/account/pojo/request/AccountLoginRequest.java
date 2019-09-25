package com.yoyo.authority.account.pojo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/5 14:17
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class AccountLoginRequest implements Serializable {

    private String name;

    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
}
