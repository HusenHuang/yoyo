package com.yoyo.authority.menu.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 @Author:MrHuang
 @Date: 2019/9/4 10:49
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
public class MenuDTO implements Serializable {

    private Integer mid;

    private String name;

    private String path;

    private Integer menuStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
