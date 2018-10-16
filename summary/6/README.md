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