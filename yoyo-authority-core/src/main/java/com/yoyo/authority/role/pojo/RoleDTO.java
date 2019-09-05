package com.yoyo.authority.role.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/4 10:48
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable {

    private String rid;

    private String name;

    private Integer roleStatus;

    private List<Integer> bindMenuId;

    private String createTime;

    private String updateTime;
}
