package com.yoyo.authority.menu.pojo;

import com.yoyo.framework.mongo.MongoVersion;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/6 14:32
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class MenuGetRsp implements Serializable {

    private String parentId;

    private String name;

    private String path;

    private Integer menuStatus;

    private Integer ordered;
}
