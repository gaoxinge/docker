# gliderlabs/registrator

## command

```
$ docker run -d -p 8400:8400 -p 8500:8500 -p 8600:53/udp --name node1 gliderlabs/consul-server -bootstrap -advertise 10.0.75.1
$ docker run -d --name registrator --net=host --volume=//var/run/docker.sock:/tmp/docker.sock gliderlabs/registrator consul://127.0.0.1:8500
$ docker run -d -P redis
```

```
```

## reference

- [gliderlabs/registrator](https://hub.docker.com/r/gliderlabs/registrator/)
- [单机使用Docker host网络安装consul和registrator](https://blog.csdn.net/gsying1474/article/details/51773391)