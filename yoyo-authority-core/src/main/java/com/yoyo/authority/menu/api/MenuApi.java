package com.yoyo.authority.menu.api;

import com.yoyo.authority.menu.pojo.MenuAddReq;
import com.yoyo.authority.menu.pojo.MenuGetRsp;
import com.yoyo.authority.menu.pojo.MenuUpdateReq;
import com.yoyo.authority.menu.service.IMenuService;
import com.yoyo.framework.api.RTRaw;
import com.yoyo.framework.api.RTRawWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/***
 @Author:MrHuang
 @Date: 2019/9/5 18:41
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RequestMapping("/menu")
@RestController
public class MenuApi {

    @Autowired
    private IMenuService menuService;

    @PostMapping("/add")
    public RTRaw<Boolean> addMenu(@Validated @RequestBody MenuAddReq req) {
        boolean result = menuService.addMenu(req.getName(), req.getPath(), req.getParentId(), req.getOrdered());
        return RTRawWrite.success(result);
    }

    @PostMapping("/update")
    public RTRaw<Boolean> updateMenu(@Validated @RequestBody MenuUpdateReq req) {
        boolean result = menuService.updateMenu(req.getMid(), req.getName(), req.getPath(), req.getParentId(), req.getOrdered());
        return RTRawWrite.success(result);
    }

    @GetMapping("/get/{mid}")
    public RTRaw<MenuGetRsp> getMenu(@PathVariable String mid) {
        MenuGetRsp result = menuService.getMenu(mid);
        return RTRawWrite.success(result);
    }

    @DeleteMapping("/delete/{mid}")
    public RTRaw<Boolean> deleteMenu(@PathVariable String mid) {
        boolean result = menuService.deleteMenu(mid);
        return RTRawWrite.success(result);
    }
}
