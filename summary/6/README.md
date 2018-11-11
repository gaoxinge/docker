## cgroup

全称control group，用于设置CPU，内存，IO资源的限额：

- CPU：/sys/fs/cgroup/cpu/docker/containerid
- 内存：/sys/fs/cgroup/memory/docker/containerid
- IO：/sys/fs/cgroup/blkio/docker/containerid 

## namespace

实现了容器间资源的隔离，类似于操作系统实现了进程间资源的隔离：

- mount：文件系统
- UTS：主机名
- IPC：共享内存和信号量
- PID：进程ID
- network：网卡，路由，IP
- user：用户

## network

```
busybox  busybox    httpd            busybox  busybox
   |        |         |                  |      |
----------------------------------------------------
| none  |  host  |  bridge  |  my_net  |  my_net2  |
|       |        | (docker0)|          |           | 
----------------------------------------------------
| null  |  host  |             bridge              |
----------------------------------------------------
```

```
> docker network create --driver bridge my_net
> docker network create --driver --subnet 172.22.16.0/24 --gateway 172.22.16.1 my_net2
```

```
> docker network ls
NETWORK ID          NAME                DRIVER              SCOPE
a89f78b94161        bridge              bridge              local
533336a61ad6        host                host                local
801312bb18f2        my_net              bridge              local
edc8c342ae01        my_net2             bridge              local
8cecc96fb0b5        none                null                local
```

- none：没有网络配置

```
> docker run -it --network=none busybox
```

- host：共享host的网络配置

```
> docker run -it --network=host busybox
```

- bridge

```
> docker run -d httpd                                            # 172.17.0.2/16
> docker run -it --network=my_net2 busybox                       # 172.22.16.2/24
> docker run -it --network=my_net2 --ip 172.22.16.8 busybox      # 172.22.16.8/24
```

```
> docker inspect bridge                                          # subnet: 172.17.0.0/16 | gateway: 172.17.0.1
> docker inspect my_net                                          # subnet: 172.18.0.0/16 | gateway: 172.18.0.1/16
> docker inspect my_net2                                         # subnet: 172.22.16.0/24 | gateway: 172.22.16.1
```