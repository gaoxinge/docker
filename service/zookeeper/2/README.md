# zookeeper cluster

## command

```
$ docker run -d -p 2181:2181 --restart always --name zoo1 -e "ZOO_MY_ID=1" -e "ZOO_SERVERS=server.1=0.0.0.0:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888" zookeeper
$ docker run -d -p 2182:2181 --restart always --name zoo2 -e "ZOO_MY_ID=2" -e "ZOO_SERVERS=server.1=zoo1:2888:3888 server.2=0.0.0.0:2888:3888 server.3=zoo3:2888:3888" zookeeper
$ docker run -d -p 2183:2181 --restart always --name zoo3 -e "ZOO_MY_ID=3" -e "ZOO_SERVERS=server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=0.0.0.0:2888:3888" zookeeper
```

## referecne

- [docker zookeeper 集群搭建](https://www.liangzl.com/get-article-detail-16856.html)
- [Docker中搭建zookeeper集群](https://www.cnblogs.com/znicy/p/7717426.html)