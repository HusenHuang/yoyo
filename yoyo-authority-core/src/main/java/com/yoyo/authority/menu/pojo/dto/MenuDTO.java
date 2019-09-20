package com.yoyo.authority.menu.pojo.dto;

import com.yoyo.framework.mongo.annotation.MongoCreateTime;
import com.yoyo.framework.mongo.annotation.MongoUpdateTime;
import com.yoyo.framework.mongo.annotation.MongoVersion;
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
    private Integer ordered;

    @Field
    @MongoVersion
    private int version;

    @Field
    @MongoCreateTime
    private String createTime;


    @Field
    @MongoUpdateTime
    private String updateTime;
}
