package com.yoyo.authority.role.pojo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/6 14:41
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RoleBindMenuReq implements Serializable {

    @NotBlank(message = "角色ID不能为空")
    private String rid;

    private List<String> midList;
}
