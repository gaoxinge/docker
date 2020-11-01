package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorListenerPathCacheZookeeper {
    private static final int SECOND = 1000;

    public static void main(String[] args) throws Exception {
        String root = "/hifreud";
        String path = root + "/sayhi";
        String path2 = root + "/sayhello";
        String data = "hi freud!";
        String dataAgain = "hi freud again!";

        RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
        client.start();
        System.out.println("Server connected...");

        PathChildrenCache pathCache = new PathChildrenCache(client, root, true);
        pathCache.start();
        pathCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case INITIALIZED:
                    case CONNECTION_LOST:
                    case CONNECTION_RECONNECTED:
                    case CONNECTION_SUSPENDED:
                        System.out.println("[Callback]Event [" + event.getType().toString() + "] ");
                        break;
                    case CHILD_ADDED:
                    case CHILD_REMOVED:
                    case CHILD_UPDATED:
                        System.out.println("[Callback]Event [" + event.getType().toString() + "] Path [" + event.getData().getPath() + "] data change to: " + new String(event.getData().getData()));
                        break;
                    default:
                        System.out.println("[Callback]Event [Error] ");
                }
            }
        });
        System.out.println("Listener added success...");

        if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
            System.out.println("Create node [" + path + "] with data [" + data + "]");
        }
        if (client.checkExists().forPath(path2) == null) {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path2, data.getBytes());
            System.out.println("Create node [" + path2 + "] with data [" + data + "]");
        }

        if (client.checkExists().forPath(path) != null) {
            client.setData().forPath(path, dataAgain.getBytes());
            System.out.println("Set data to node [" + path + "] data: " + dataAgain);
        }

        if (client.checkExists().forPath(path2) != null) {
            client.delete().guaranteed().forPath(path2);
            System.out.println("Delete node [" + path2 + "].");
        }
        if (client.checkExists().forPath(root) != null) {
            client.delete().deletingChildrenIfNeeded().forPath(root);
            System.out.println("Delete node [" + root + "] use recursion.");
        }

        pathCache.close();
        client.close();
        System.out.println("Server closed...");
    }
}
