package com.yoyo.authority.role.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/5 17:59
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RoleUpdateReq implements Serializable {

    @NotBlank(message = "角色ID不能为空")
    private String rid;

    @NotBlank(message = "角色名称不能为空")
    private String name;

    private String remark;
}
