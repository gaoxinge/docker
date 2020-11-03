package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorCounterDistributedAtomicLongZookeeper {
    private static final int SECOND = 1000;

    static class Task implements Runnable {
        private final int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
                CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
                client.start();
                System.out.println("Thread [" + index + "]Server connected...");

                String path = "/curator_counter/distribute_atomic_long";
                DistributedAtomicLong count = new DistributedAtomicLong(client, path, new ExponentialBackoffRetry(SECOND, 3));

                Thread.sleep((index + 1) * SECOND);
                AtomicValue<Long> al = count.get();
                System.out.println("Thread [" + index + "] get new Long value [" + al.postValue() + "] result status [" + al.succeeded() + "]");
                count.increment();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            service.submit(new Task(i));
        }
        Thread.sleep(15 * SECOND);
        service.shutdown();
    }
}
