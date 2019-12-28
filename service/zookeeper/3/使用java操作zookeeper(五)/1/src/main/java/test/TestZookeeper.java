package test;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import java.util.Collections;
import java.util.List;


public class TestZookeeper implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (event.getState() == KeeperState.SyncConnected) {
            System.out.println("zookeeper state is " + KeeperState.SyncConnected);
        }
    }

    public static void main(String[] args) throws Exception {
        // create session
        ZooKeeper zookeeper1 = new ZooKeeper("127.0.0.1:2181", 5000, new TestZookeeper());
        System.out.println(zookeeper1.getState());
        Thread.sleep(2000);
        System.out.println(zookeeper1.getState());

        long sessionId = zookeeper1.getSessionId();
        byte[] sessionPasswd = zookeeper1.getSessionPasswd();
        ZooKeeper zookeeper2 = new ZooKeeper("127.0.0.1:2181", 5000, new TestZookeeper(), sessionId, sessionPasswd);
        System.out.println(zookeeper2.getState());
        Thread.sleep(2000);
        System.out.println(zookeeper2.getState());
        zookeeper2.close();

        // create node
        zookeeper1.create("/node", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zookeeper1.create("/node1", "123".getBytes(), Collections.singletonList(new ACL(ZooDefs.Perms.CREATE, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);

        zookeeper1.create("/node2",
                "123".getBytes(),
                Collections.singletonList(new ACL(ZooDefs.Perms.CREATE, new Id("ip", "127.0.0.1"))),
                CreateMode.EPHEMERAL_SEQUENTIAL,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("rc:" + rc);
                        System.out.println("path:" + path);
                        System.out.println("ctx:" + ctx);
                        System.out.println("name:" + name);
                    }
                },
                "传给服务端的内容, 会在异步回调时传回来");
        Thread.sleep(2000);

        zookeeper1.create("/node3",
                "123".getBytes(),
                Collections.singletonList(new ACL(ZooDefs.Perms.ALL, new Id("digest", "wangsaichao:G2RdrM8e0u0f1vNCj/TI99ebRMw="))),
                CreateMode.PERSISTENT,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        System.out.println("rc:" + rc);
                        System.out.println("path:" + path);
                        System.out.println("ctx:" + ctx);
                        System.out.println("name:" + name);
                    }
                },
                "传给服务端的内容, 会在异步回调时传回来");
        Thread.sleep(2000);

        // get data
        zookeeper1.addAuthInfo("digest","wangsaichao:123456".getBytes());
        byte[] data = zookeeper1.getData("/node3", new TestZookeeper(), new Stat());
        System.out.println(new String(data));
        zookeeper1.getData("/node3",
                false,
                new AsyncCallback.DataCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                        System.out.println("rc:" + rc);
                        System.out.println("path:" + path);
                        System.out.println("ctx:" + ctx);
                        System.out.println("data:" + new String(data));
                        System.out.println("stat:" + stat);
                    }
                },
                "传给服务端的内容, 会在异步回调时传回来");
        Thread.sleep(2000);

        // set data
        zookeeper1.addAuthInfo("digest","wangsaichao:123456".getBytes());
        Stat stat = zookeeper1.setData("/node3", "嗨喽".getBytes(), -1);
        System.out.println(stat);
        zookeeper1.setData("/node3",
                "helloword".getBytes(),
                -1,
                new AsyncCallback.StatCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, Stat stat) {
                        System.out.println("rc:" + rc);
                        System.out.println("path:" + path);
                        System.out.println("ctx:" + ctx);
                        System.out.println("stat:" + stat);
                    }
                },
                "传给服务端的内容, 会在异步回调时传回来");
        Thread.sleep(2000);

        // delete node
        zookeeper1.delete("/node1",-1);
        zookeeper1.delete("/node2",
                -1,
                new AsyncCallback.VoidCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx) {
                        System.out.println("rc:" + rc);
                        System.out.println("path:" + path);
                        System.out.println("ctx:" + ctx);
                    }
                },
                "传给服务端的内容, 会在异步回调时传回来");
        Thread.sleep(2000);

        // exist
        Stat exists = zookeeper1.exists("/node2", event -> System.out.println("留下监视"));
        System.out.println("判断节点是否存在:" + exists);
        zookeeper1.exists("/node2",
                event -> System.out.println("留下监视"),
                new AsyncCallback.StatCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, Stat stat) {
                        System.out.println("rc:" + rc);
                        System.out.println("path:" + path);
                        System.out.println("ctx:" + ctx);
                        System.out.println("判断节点是否存在:" + stat);
                    }
                },
                "传给服务端的内容, 会在异步回调时传回来");
        Thread.sleep(2000);

        // acl
        zookeeper1.addAuthInfo("digest","helloworld:123456".getBytes());
        zookeeper1.addAuthInfo("digest","wangsaichao:123456".getBytes());
        Stat auth = zookeeper1.setACL("/node3", Collections.singletonList(new ACL(ZooDefs.Perms.ALL, new Id("auth", "helloworld:123456"))), -1);
        System.out.println(auth);
        List<ACL> acl = zookeeper1.getACL("/node3", new Stat());
        System.out.println(acl);
    }
}
