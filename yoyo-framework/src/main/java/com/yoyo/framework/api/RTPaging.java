package com.yoyo.framework.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/***
 @Author:MrHuang
 @Date: 2019/9/19 16:10
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Data
@Accessors(chain = true)
public class RTPaging<T> implements Serializable {

    /**
     * 当前页数
     */
    private long pageNow;

    /**
     * 每页显示条数
     */
    private long pageSize;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总记录数
     */
    private long totalRecord;

    /**
     * 列表对象
     */
    private List<T> record;


    public static long getSkip(long pageNow, int pageSize) {
        return (pageNow - 1) * pageSize;
    }

    public static long getTotalPage(long totalRecord, int pageSize) {
        return  (totalRecord + pageSize -1) / pageSize;
    }
}
