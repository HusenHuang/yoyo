package com.yoyo.authority.account.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 @Author:MrHuang
 @Date: 2019/9/3 22:30
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
public class AccountDTO implements Serializable {

    private Integer aid;

    private String name;

    private String password;

    private Integer activeState;

    private String email;

    private Integer bindRoleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
