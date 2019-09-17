package com.yoyo.framework.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/***
 @Author:MrHuang
 @Date: 2019/9/17 11:28
 @DESC: TODO
 @VERSION: 1.0
 ***/
@Component
@Slf4j
public class DistributedLockWatch {

    private int baseSleepTimeMs = 1000;

    private int maxRetries = 3;

    private int sessionTimeoutMs = 60000;

    private int connectionTimeoutMs = 15000;

    /**
     * zooKeeper 服务地址, 单机格式为:(127.0.0.1:2181), 集群格式为:(127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183)
     */
    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    /**
     * Curator 客户端重试策略
     */
    private RetryPolicy retry = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);


    /**
     * 不可重入共享锁
     * 获取不到会一直阻塞
     * @param lockPath 锁Path
     * @return
     */
    public boolean acquire(CuratorFramework client, String lockPath) {
        try {
            new InterProcessSemaphoreMutex(client, lockPath).acquire();
            return true;
        } catch (Exception e) {
            log.error("DistributedLock acquire fail", e);
            return false;
        }
    }

    /**
     * 不可重入共享锁
     * 超时返回false
     * @param lockPath 锁Path
     * @return
     */
    public boolean acquire(CuratorFramework client, String lockPath, int timeoutS) {
        try {
            return new InterProcessSemaphoreMutex(client, lockPath).acquire(timeoutS, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("DistributedLock acquire fail", e);
            return false;
        }
    }

    /**
     * 释放锁
     * @param client
     */
    public void release(CuratorFramework client) {
        this.closeClient(client);
    }

    /**
     * 获取Client
     * @return
     */
    public CuratorFramework getClient() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperAddress, sessionTimeoutMs, connectionTimeoutMs, retry);
        client.start();
        return client;
    }

    /**
     * 关闭Client
     * @param client
     */
    private void closeClient(CuratorFramework client) {
        CloseableUtils.closeQuietly(client);
    }
}
