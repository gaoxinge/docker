package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorBarriersDoubleBarrierZookeeper {
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

                String path = "/curator_barrier/double_barrier";
                DistributedDoubleBarrier barrier = new DistributedDoubleBarrier (client, path, 3);
                System.out.println("Thread [" + index + "] on ready!");
                barrier.enter();
                System.out.println("Thread [" + index + "] Running!");
                barrier.leave();
                System.out.println("Thread [" + index + "] finised!");
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
        service.shutdownNow();
    }
}
