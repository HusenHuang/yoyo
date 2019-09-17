package com.yoyo.framework.redis.aspect;

import com.yoyo.framework.api.RTCode;
import com.yoyo.framework.exception.RTLimitException;
import com.yoyo.framework.redis.RedisDistributedLimitUtils;
import com.yoyo.framework.redis.annotation.RedisDistributedLimit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/***
 @Author:MrHuang
 @Date: 2019/7/24 17:17
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Slf4j
@Aspect
@Component
public class RedisDistributedLimitAspect {

    /**
     * 限流Key前缀
     */
    private static final String DEFAULT_LIMIT_KEY_PRE = "RedisDistributedLimit:";

    /**
     * 定义环绕通知
     * @annotation(distributedLock) 切入点
     */
    @Before("@annotation(redisDistributedLimit)")
    public void before(JoinPoint point, RedisDistributedLimit redisDistributedLimit){
        // 获取注解值
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(redisDistributedLimit);
        String limitKey = StringUtils.isEmpty(annotationAttributes.get("value")) ? point.getSignature().getDeclaringTypeName() + "#" + point.getSignature().getName() : (String)annotationAttributes.get("value");
        int limitNum = redisDistributedLimit.limitNum();
        int expireSecond = redisDistributedLimit.expireSecond();
        String distributedLimitKey = DEFAULT_LIMIT_KEY_PRE + limitKey;
        boolean isLimit = RedisDistributedLimitUtils.isLimit(distributedLimitKey, limitNum, expireSecond);
        log.info("DistributedLimitAspect isLimit = {}", isLimit);
        if (isLimit) {
            throw new RTLimitException(RTCode.LIMIT_FAIL.getMsg());
        }
    }
}
