package com.yoyo.framework.redis.aspect;

import com.yoyo.framework.api.RTCode;
import com.yoyo.framework.exception.RTGetLockException;
import com.yoyo.framework.redis.RedisDistributedLockUtils;
import com.yoyo.framework.redis.annotation.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

/***
 @Author:MrHuang
 @Date: 2019/7/11 11:12
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Slf4j
@Aspect
@Component
public class RedisDistributedLockAspect {

    /**
     * 分布式锁前缀
     */
    private static final String DEFAULT_LOCK_KEY_PRE = "RedisDistributedLock:";

    /**
     * 定义环绕通知
     * @annotation(distributedLock) 切入点
     */
    @Around(value = "@annotation(redisDistributedLock)")
    public Object around(ProceedingJoinPoint point, RedisDistributedLock redisDistributedLock) throws Throwable {
        // 获取注解值
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(redisDistributedLock);
        String clientId = StringUtils.isEmpty(redisDistributedLock.clientId()) ? UUID.randomUUID().toString() : redisDistributedLock.clientId();
        String lockKey = StringUtils.isEmpty(annotationAttributes.get("value")) ? point.getSignature().getDeclaringTypeName() + "#" + point.getSignature().getName() : (String)annotationAttributes.get("value");
        int expireSecond = redisDistributedLock.expireSecond();
        boolean isBlock = redisDistributedLock.sleppMilliSecond() != 0 && redisDistributedLock.blockMilliSecond() != 0;
        // 分布式锁Key
        String distributedLockKey =  DEFAULT_LOCK_KEY_PRE + lockKey;
        // 1.获取锁
        boolean isGetLock;
        if (isBlock) {
            // 要阻塞的
            isGetLock = RedisDistributedLockUtils.getBlockLock(distributedLockKey, clientId, expireSecond, redisDistributedLock.blockMilliSecond(), redisDistributedLock.sleppMilliSecond());
        } else {
            // 非阻塞的
            isGetLock = RedisDistributedLockUtils.getNoBlockLock(distributedLockKey, clientId, expireSecond);
        }
        // 锁获取失败 直接抛出异常
        if (!isGetLock) {
            throw new RTGetLockException(RTCode.GET_LOCK_FAIL.getMsg());
        }
        // 2. 执行代码逻辑
        Object result = null;
        try {
            result = point.proceed();
        } finally {
            // 3. 释放锁
            RedisDistributedLockUtils.releaseLock(distributedLockKey, clientId);
        }
        return result;
    }
}
