package com.yoyo.framework.mongo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/***
 @Author:MrHuang
 @Date: 2019/9/20 20:14
 @DESC: TODO 集合自增主键存储表
 @VERSION: 1.0
 ***/

@Document(collection = "t_auto_key")
@Data
@Accessors(chain = true)
public class MongoAutoKeyTable implements Serializable {

    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 集合名称
     */
    @Field
    private String collName;

    /**
     * 当前序列ID
     */
    @Field
    private Long seqId;
}
