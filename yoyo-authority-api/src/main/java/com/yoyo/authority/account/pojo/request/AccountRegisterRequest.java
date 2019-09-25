package com.yoyo.authority.account.pojo.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/5 11:12
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class AccountRegisterRequest implements Serializable {

    @NotBlank(message = "注册名不能为空")
    private String name;

    @Length(min = 6, max = 12, message = "密码长度必须在6位到12位之间")
    private String password;

    @NotBlank(message = "注册邮箱不能为空")
    private String email;
}
