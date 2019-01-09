# consul

## command

```
# not ok: listen tcp 10.0.75.1:8300: bind: cannot assign requested address
$ docker run -d -p 1234:8500 --name node1 consul agent -server -bootstrap-expect=1 -retry-join=3 -bind 10.0.75.1 -client 0.0.0.0 -ui
```

```
# ok: http://10.0.75.1:1234 or http://localhost:1234
$ docker run -d -p 1234:8500 --name node1 consul agent -server -bootstrap-expect=1 -retry-join=3 -bind 127.0.0.1 -client 0.0.0.0 -ui
```

## referecne

- [consul](https://hub.docker.com/_/consul)
- [单节点下使用docker部署consul](https://www.cnblogs.com/magic-chenyang/p/7975677.html)
- [Docker-Consul入门（一） 简介，用途，环境搭建-单机](https://blog.csdn.net/yinwaner/article/details/80762757)