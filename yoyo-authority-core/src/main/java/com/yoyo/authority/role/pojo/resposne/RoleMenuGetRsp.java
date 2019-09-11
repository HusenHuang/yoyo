package com.yoyo.authority.role.pojo.resposne;

import com.yoyo.authority.menu.pojo.MenuVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/6 15:11
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RoleMenuGetRsp implements Serializable {

    private List<MenuVO> menuList;
}
