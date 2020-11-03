package com.freud.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

public class CuratorUtilsZKPathsZookeeper {
    private static final int SECOND = 1000;

    public static void main(String[] args) throws Exception {
        String root = "/curator_utils";
        String path = "/zkpaths";

        RetryPolicy rp = new ExponentialBackoffRetry(SECOND, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").sessionTimeoutMs(5 * SECOND).connectionTimeoutMs(3 * SECOND).retryPolicy(rp).build();
        client.start();
        System.out.println("Server connected...");

        ZooKeeper zk = client.getZookeeperClient().getZooKeeper();
        System.out.println(ZKPaths.fixForNamespace(root, path));
        System.out.println(ZKPaths.makePath(root, path));
        System.out.println(ZKPaths.getNodeFromPath(root + path));

        ZKPaths.PathAndNode pn = ZKPaths.getPathAndNode(root + path);
        System.out.println(pn.getPath());
        System.out.println(pn.getNode());

        String dir1 = root + path + "/dir1";
        String dir2 = root + path + "/dir2";
        ZKPaths.mkdirs(zk, dir1);
        ZKPaths.mkdirs(zk, dir2);
        System.out.println(ZKPaths.getSortedChildren(zk, root + path));
        ZKPaths.deleteChildren(zk, root, true);

        client.close();
        System.out.println("Server closed...");
    }
}
