package com.yoyo.authority.account.pojo;

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
 @Date: 2019/9/3 22:30
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
@Document(collection = "t_account")
public class AccountDTO implements Serializable {

    @Id
    private String aid;

    @Field
    private String name;

    @Field
    private String password;

    /**
     * 0：待激活
     * 1：已激活
     * 2：已停用
     */
    @Field
    private Integer activeState;

    @Field
    private String email;

    @Field
    private String bindRoleId;

    @Field
    private String createTime;

    @Field
    private String updateTime;

    @MongoVersion
    private String version;
}
