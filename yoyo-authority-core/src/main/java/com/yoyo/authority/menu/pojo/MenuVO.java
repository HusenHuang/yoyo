package com.yoyo.authority.menu.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/6 15:18
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class MenuVO implements Serializable {

    private String parentId;

    private String name;

    private String path;

    private Integer menuStatus;

    private Integer ordered;
}
