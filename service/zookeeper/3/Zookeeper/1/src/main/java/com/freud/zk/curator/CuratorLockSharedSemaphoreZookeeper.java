package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class CuratorLockSharedSemaphoreZookeeper {
    private static final int SECOND = 1000;

    public static void main(String[] args) throws Exception {
        RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
        client.start();
        System.out.println("Server connected...");

        String path = "/curator_lock/shared_semaphore_lock";
        final InterProcessSemaphoreV2 lock = new InterProcessSemaphoreV2(client, path, 3);
        final CountDownLatch down = new CountDownLatch(1);

        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Lease lease = null;
                    try {
                        down.await();
                        lease = lock.acquire();

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                        Date date = new Date();
                        String orderNo = sdf.format(date);
                        System.out.println("生成的订单号是: " + orderNo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (lease != null) {
                                lock.returnLease(lease);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        down.countDown();

        Thread.sleep(10 * SECOND);
        client.close();
        System.out.println("Server closed...");
    }
}
