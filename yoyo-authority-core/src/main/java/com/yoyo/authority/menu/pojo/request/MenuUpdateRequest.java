package com.yoyo.authority.menu.pojo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/6 14:11
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class MenuUpdateRequest implements Serializable {

    @NotBlank(message = "菜单ID不能为空")
    private String mid;

    @NotBlank(message = "菜单名不能为空")
    private String name;

    private String path;

    private String parentId;

    private int ordered;
}
