package com.yoyo.framework.zookeeper.aspect;

import com.yoyo.framework.api.RTCode;
import com.yoyo.framework.exception.RTGetLockException;
import com.yoyo.framework.zookeeper.ZKDistributedLockWatch;
import com.yoyo.framework.zookeeper.annotation.ZKDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/***
 @Author:MrHuang
 @Date: 2019/9/17 14:07
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Slf4j
@Aspect
@Component
public class ZKDistributedLockAspect {

    /**
     * 分布式锁前缀
     */
    private static final String DEFAULT_LOCK_PATH_PRE = "/DistributedLockPath:";


    @Autowired
    private ZKDistributedLockWatch watch;
    /**
     * 定义环绕通知
     * @annotation(distributedLock) 切入点
     */
    @Around(value = "@annotation(ZKDistributedLock)")
    public Object around(ProceedingJoinPoint point, ZKDistributedLock ZKDistributedLock) throws Throwable {
        // 获取注解值
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(ZKDistributedLock);
        String lockPath = (String)annotationAttributes.get("value");
        Assert.hasLength(lockPath, "lockPath not empty");
        int expireSecond = ZKDistributedLock.expireSecond();
        // 分布式锁
        String distributedLockPath =  DEFAULT_LOCK_PATH_PRE + lockPath;
        CuratorFramework client = watch.getClient();
        // 1.获取锁
        boolean isGetLock = watch.acquire(client, distributedLockPath, expireSecond);
        // 锁获取失败 直接抛出异常
        if (!isGetLock) {
//            watch.release(client);
            throw new RTGetLockException(RTCode.GET_LOCK_FAIL.getMsg());
        }
        // 2. 执行代码逻辑
        Object result = null;
        try {
            result = point.proceed();
        } finally {
            // 3. 释放锁
            watch.release(client);
        }
        return result;
    }
}
