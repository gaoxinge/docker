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

## 存储资源

- 数据层：由storage driver管理
  - 镜像层
  - 容器层
- volume
  - bind mount
    - docker managed volume
  - host与container共享数据
  - container与container共享数据
    - 通过host共享
    - 通过volume container共享
    - 通过data-packed volume container共享

### bind mount

```
> more D:\htdocs\index.html
<html><body><h1>This is a file in host file system!</h1></body></html>
> cat D:\htdocs\index.html
<html><body><h1>This is a file in host file system!</h1></body></html>

# 把host中的文件挂载（bind mount）到container中
> docker run -d -p 80:80 -v /d/htdocs:/usr/local/apache2/htdocs httpd
> docker run -d -p 80:80 -v D:/htdocs:/usr/local/apache2/htdocs httpd
```

### docker managed volume

```
> docker run -d -p 80:80 -v /usr/local/apache2/htdocs httpd
21
> docker inspect 21
> docker volume ls
f4
> docker volume inspect f4
```

```
> docker rm -v 21
```

```
> docker rm 21
> docker volume rm f4
```

### reference

- [在windows10上使用docker哪些坑](https://segmentfault.com/a/1190000006799421)
- [关于Docker目录挂载的总结](https://www.cnblogs.com/ivictor/p/4834864.html)

## network

```
busybox  busybox    httpd    httpd busybox busybox busybox
   |        |         |        |      |      |       |
----------------------------------------------------------
|NIC/IP |NIC/IP  |NIC/IP    |NIC/IP      |NIC/IP |NIC/IP |
----------------------------------------------------------
| none  |  host  |  bridge  |  my_net    |  my_net2      |
|       |        | (docker0)|            |               | 
----------------------------------------------------------
| null  |  host  |             bridge                    |
----------------------------------------------------------
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

```
> docker inspect bridge       # subnet: 172.17.0.0/16 | gateway: 172.17.0.1
> docker inspect my_net       # subnet: 172.18.0.0/16 | gateway: 172.18.0.1/16
> docker inspect my_net2      # subnet: 172.22.16.0/24 | gateway: 172.22.16.1
```

```
> docker network rm my_net
> docker network rm my_net2
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

- joined

```
> docker run -d -it --network=my_net --name=web1 httpd           # 172.18.0.2/16
> docker run -d -it --network=container busybox                  # 172.18.0.2/16
```

### 端口映射

```
> docker run -d -p 80 httpd
CONTAINER ID        IMAGE               COMMAND              CREATED             STATUS              PORTS                   NAMES
65fd3f5956ce        httpd               "httpd-foreground"   19 seconds ago      Up 17 seconds       0.0.0.0:32768->80/tcp   epic_lamport
> docker port 65
80/tcp -> 0.0.0.0:32768
```

```
> curl 127.0.0.1:32768
> curl 10.0.75.1:32768
```