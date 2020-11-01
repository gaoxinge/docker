package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorListenerNodeCacheZookeeper {
    private static final int SECOND = 1000;

    public static void main(String[] args) throws Exception {
        String root = "/hifreud";
        String data = "hi freud";
        String dataAgain = "hi freud again!";

        RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
        client.start();
        System.out.println("Server connected...");

        NodeCache nodeCache = new NodeCache(client, root);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("[Callback] Node path [" + root + "] data: " + (nodeCache.getCurrentData() == null ? null : new String(nodeCache.getCurrentData().getData())));
            }
        });
        System.out.println("Listener added success...");

        if (client.checkExists().forPath(root) == null) {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(root, data.getBytes());
            System.out.println("Created node [" + root + "] with data [" + data + "]");
        }
        if (client.checkExists().forPath(root) != null) {
            client.setData().forPath(root, dataAgain.getBytes());
            System.out.println("Set data to node [" + root + "] data: " + dataAgain);
        }
        if (client.checkExists().forPath(root) != null) {
            client.delete().deletingChildrenIfNeeded().forPath(root);
            System.out.println("Delete node [" + root + "] use recursion.");
        }

        nodeCache.close();
        client.close();
        System.out.println("Server closed...");
    }
}
