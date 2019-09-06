package com.yoyo.authority.account.pojo;

import com.yoyo.authority.menu.pojo.MenuVO;
import com.yoyo.authority.role.pojo.RoleMenuGetRsp;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/5 14:18
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class AccountLoginRsp implements Serializable {

    private String tokenId;

    private String name;

    private Integer activeState;

    private String email;

    private LocalDateTime createTime;

    private List<MenuVO> menuList;
}
