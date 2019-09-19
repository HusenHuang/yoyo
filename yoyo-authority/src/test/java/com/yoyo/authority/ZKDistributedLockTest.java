package com.yoyo.authority;

import com.yoyo.framework.utils.AsyncExecutor;
import com.yoyo.framework.zookeeper.ZKDistributedLockWatch;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/***
 @Author:MrHuang
 @Date: 2019/9/19 10:41
 @DESC: TODO
 @VERSION: 1.0
 ***/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZKDistributedLockTest {

    @Autowired
    private ZKDistributedLockWatch watch;

    @Test
    public void testZkDistLock() {
        CuratorFramework client = watch.getClient();
        boolean result = watch.acquire(client, "/abcTest", 2);
        System.out.println(Thread.currentThread().getName() + " result = " + result);
        boolean quit = true;
//        AsyncExecutor.execute(() -> {
//            while (true) {
//                CuratorFramework c1 = watch.getClient();
//                boolean result2 = watch.acquire(c1, "/abcTest", 2);
//                System.out.println(Thread.currentThread().getName() + " result = " + result2);
//                watch.release(c1);
//            }
//        });
//        while (quit) {
//
//        }
        watch.release(client);
        System.exit(0);
    }

}
