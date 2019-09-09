package com.yoyo.authority.menu.pojo;

import com.yoyo.framework.mongo.MongoLogicDelete;
import com.yoyo.framework.mongo.MongoVersion;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 @Author:MrHuang
 @Date: 2019/9/4 10:49
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
@Document(collection = "t_menu")
public class MenuDTO implements Serializable {

    @Id
    private String mid;

    @Field
    private String parentId;

    @Field
    private String name;

    @Field
    private String path;

    @Field
    private Integer menuStatus;

    @Field
    private String createTime;

    @Field
    private String updateTime;

    @Field
    private Integer ordered;

    @Field
    @MongoVersion
    private String version;

    @Field
    @MongoLogicDelete
    private Integer delStatus;
}
