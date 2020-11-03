package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.*;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorQueueSimpleDistributedQueueZookeeper {
    private static final int SECOND = 1000;

    static class Task implements Callable<Void> {
        private final int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public Void call() throws Exception {
            RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
            CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
            client.start();
            System.out.println("Thread [" + index + "]Server connected...");

            String path = "/curator_queue/distributed_queue";
            SimpleDistributedQueue queue = new SimpleDistributedQueue(client, path);
            if (index == 4) {
                Thread.sleep(3 * SECOND);
                for (int i = 0; i < 20; i++) {
                    queue.offer(("message " + i).getBytes());
                }
            }

            while (true) {
                System.out.println("Thread [" + index + "] get queue value: " + new String(queue.take()));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            service.submit(new Task(i));
        }
        Thread.sleep(20 * SECOND);
        service.shutdown();
    }
}
