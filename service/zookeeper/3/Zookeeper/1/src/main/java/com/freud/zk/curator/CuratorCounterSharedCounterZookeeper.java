package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorCounterSharedCounterZookeeper {
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

                String path = "/curator_counter/shared_counter";
                SharedCount count = new SharedCount(client, path, 100);
                count.start();
                count.addListener(new SharedCountListener() {
                    @Override
                    public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
                        System.out.println("Thread [" + index + "][Callback]Count changed to [" + newCount + "]!");
                    }

                    @Override
                    public void stateChanged(CuratorFramework client, ConnectionState newState) {
                        System.out.println("Thread [" + index + "][Callback]State changed!");
                    }
                });

                Thread.sleep((index + 1) * SECOND);
                count.setCount(index);
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
