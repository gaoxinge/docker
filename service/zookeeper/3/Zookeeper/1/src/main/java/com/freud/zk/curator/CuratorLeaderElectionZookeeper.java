package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorLeaderElectionZookeeper {
    private static final int SECOND = 1000;
    private static int count = 1;

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

                String path = "/leader_selector";
                if (client.checkExists().forPath(path) == null) {
                    client.create().creatingParentsIfNeeded().forPath(path);
                }

                LeaderSelector selector = new LeaderSelector(client, path, new LeaderSelectorListener() {
                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        System.out.println("Thread [" + index + "]Do some business work...timestamp [" + System.currentTimeMillis() + "] times [" + count++ + "]");
                    }

                    @Override
                    public void stateChanged(CuratorFramework client, ConnectionState newState) {
                        System.out.println("Thread [" + index + "][Callback] State changed to: " + newState.name());
                    }
                });
                selector.autoRequeue();
                selector.start();
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
        Thread.sleep(10 * SECOND);
        service.shutdown();
    }
}
