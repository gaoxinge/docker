package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorBarriersBarrierZookeeper {
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

                String path = "/curator_barrier/distribute_barrier";
                DistributedBarrier barrier = new DistributedBarrier(client, path);
                System.out.println("Thread [" + index + "] on ready!");
                barrier.setBarrier();
                barrier.waitOnBarrier();
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

        RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
        client.start();
        System.out.println("Server connected...");

        Thread.sleep(2 * SECOND);
        String path = "/curator_barrier/distribute_barrier";
        DistributedBarrier barrier = new DistributedBarrier(client, path);
        barrier.removeBarrier();

        Thread.sleep(SECOND);
        service.shutdownNow();
    }
}
