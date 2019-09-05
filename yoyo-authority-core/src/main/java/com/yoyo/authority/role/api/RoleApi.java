package com.yoyo.authority.role.api;

import com.yoyo.authority.role.pojo.RoleAddReq;
import com.yoyo.authority.role.pojo.RoleUpdateReq;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTRaw;
import com.yoyo.framework.api.RTRawWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 @Author:MrHuang
 @Date: 2019/9/5 17:06
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RestController
@RequestMapping("/role")
public class RoleApi {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/add")
    public RTRaw<Boolean> add(@Validated @RequestBody RoleAddReq req) {
        boolean result = roleService.addRole(req.getName(), req.getRemark());
        return RTRawWrite.success(result);
    }

    @RequestMapping("/update")
    public RTRaw<Boolean> update(@Validated @RequestBody RoleUpdateReq req) {
        boolean result = roleService.updateRole(req.getRid(), req.getName(), req.getRemark());
        return RTRawWrite.success(result);
    }
}
