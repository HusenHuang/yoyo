package com.yoyo.authority.role.api;

import com.yoyo.authority.role.pojo.request.RoleAddRequest;
import com.yoyo.authority.role.pojo.request.RoleBindMenuRequest;
import com.yoyo.authority.role.pojo.resposne.RoleMenuGetResponse;
import com.yoyo.authority.role.pojo.request.RoleUpdateRequest;
import com.yoyo.authority.role.service.IRoleApiService;
import com.yoyo.authority.role.service.IRoleService;
import com.yoyo.framework.api.RTRaw;
import com.yoyo.framework.api.RTRawWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private IRoleApiService roleApiService;

    @PostMapping("/add")
    public RTRaw<Boolean> add(@Validated @RequestBody RoleAddRequest req) {
        boolean result = roleApiService.addRole(req.getName(), req.getRemark());
        return RTRawWrite.success(result);
    }

    @PostMapping("/update")
    public RTRaw<Boolean> update(@Validated @RequestBody RoleUpdateRequest req) {
        boolean result = roleApiService.updateRole(req.getRid(), req.getName(), req.getRemark());
        return RTRawWrite.success(result);
    }

    @PostMapping("/bindMenu")
    public RTRaw<Boolean> bindMenu(@Validated @RequestBody RoleBindMenuRequest req) {
        boolean result = roleApiService.bindMenu(req.getRid(), req.getMidList());
        return RTRawWrite.success(result);
    }


    @GetMapping("/getRoleMenu/{roleId}")
    public RTRaw<RoleMenuGetResponse> getRoleMenu(@Validated @PathVariable String roleId) {
        RoleMenuGetResponse result = roleApiService.getRoleMenuTree(roleId);
        return RTRawWrite.success(result);
    }
}
