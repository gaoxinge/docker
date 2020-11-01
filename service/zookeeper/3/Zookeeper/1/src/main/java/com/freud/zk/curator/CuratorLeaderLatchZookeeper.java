package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorLeaderLatchZookeeper {
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

                String path = "/leader_latch";
                if (client.checkExists().forPath(path) == null) {
                    client.create().creatingParentsIfNeeded().forPath(path);
                }

                LeaderLatch latch = new LeaderLatch(client, path);
                latch.addListener(new LeaderLatchListener() {
                    @Override
                    public void isLeader() {
                        System.out.println(MessageFormat.format("Thread [" + index + "] I am the leader... timestamp [{0}]", System.currentTimeMillis()));
                    }

                    @Override
                    public void notLeader() {
                        System.out.println(MessageFormat.format("Thread [" + index + "] I am not the leader... timestamp [{0}]", System.currentTimeMillis()));
                    }
                });
                latch.start();

                latch.close();
                client.close();
                System.out.println("Thread [" + index + "] Server closed...");
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
        Thread.sleep(50 * SECOND);
        service.shutdown();
    }
}
