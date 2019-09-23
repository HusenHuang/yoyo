package com.yoyo.authority.menu.pojo.response;

import com.yoyo.authority.menu.pojo.MenuVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/23 11:17
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class MenuListShowRsp implements Serializable {

    private List<MenuVO> vos;
}
