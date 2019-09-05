package com.yoyo.authority.role.pojo;

import com.yoyo.framework.mongo.MongoVersion;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/4 10:48
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable {

    @Id
    private String rid;

    @Field
    private String name;

    @Field
    private String remark;

    @Field
    private Integer roleStatus;

    @Field
    private List<Integer> bindMenuId;

    @Field
    private String createTime;

    @Field
    private String updateTime;

    @MongoVersion
    private String version;
}
