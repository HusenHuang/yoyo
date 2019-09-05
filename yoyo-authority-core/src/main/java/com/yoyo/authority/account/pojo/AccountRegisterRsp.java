package com.yoyo.authority.account.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 @Author:MrHuang
 @Date: 2019/9/5 11:04
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class AccountRegisterRsp implements Serializable {

    private String aid;

    private String name;

    private String password;

    private Integer activeState;

    private String email;

    private LocalDateTime createTime;
}
