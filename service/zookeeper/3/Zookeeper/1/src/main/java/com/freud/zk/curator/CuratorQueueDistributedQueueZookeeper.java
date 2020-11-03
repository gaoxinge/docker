package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorQueueDistributedQueueZookeeper {
    private static final int SECOND = 1000;

    static class StringQueueConsumer implements QueueConsumer<String> {
        private int index;

        public StringQueueConsumer(int index) {
            this.index = index;
        }

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {

        }

        @Override
        public void consumeMessage(String message) throws Exception {
            System.out.println("Thread [" + index + "] get the queue value: " + message);
        }
    }

    static class StringQueueSerializer implements QueueSerializer<String> {

        @Override
        public byte[] serialize(String item) {
            return item.getBytes();
        }

        @Override
        public String deserialize(byte[] bytes) {
            return new String(bytes);
        }
    }

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
            DistributedQueue<String> queue = QueueBuilder.builder(client, new StringQueueConsumer(index), new StringQueueSerializer(), path).buildQueue();
            queue.start();
            if (index == 4) {
                Thread.sleep(3 * SECOND);
                for (int i = 0; i < 20; i++) {
                    queue.put("message " + i);
                }
            }
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            service.submit(new Task(i));
        }
        Thread.sleep(10 * SECOND);
        service.shutdown();
    }
}
