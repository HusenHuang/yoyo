package com.yoyo.authority.role.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/5 17:59
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RoleUpdateRsp implements Serializable {

    private boolean opStatus;

}
