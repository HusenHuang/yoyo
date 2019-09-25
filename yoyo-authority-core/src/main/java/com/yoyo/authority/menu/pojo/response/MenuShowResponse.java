package com.yoyo.authority.menu.pojo.response;

import com.yoyo.authority.menu.pojo.MenuVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/6 14:32
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class MenuShowResponse implements Serializable {

    private MenuVO menu;
}
