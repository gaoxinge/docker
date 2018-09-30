## 创建

```
$ docker create xxx
```

## 运行

```
$ docker run ubuntu pwd
$ docker run ubuntu /bin/bash -c "while true ; do sleep 1 ; done"
$ docker run -d ubuntu /bin/bash -c "while true ; do sleep 1 ; done"
$ docker run -it ubuntu
```

### 指定名字

```
$ docker run --name "my_http_server" -d httpd
```

## 进入

```
$ docker attach 969fac2f0e41
$ docker logs -f 969fac2f0e41
$ docker exec -it 969fac2f0e41 bash
```

## 停止

```
$ docker stop fe39cc2ccc8b
```

## 删除

```
$ docker rm xxx
$ docker rm -v $(docker ps -a -q -f status=exited)
```

## 查看

```
$ docker ps
$ docker ps -a
```

## 其他

```
$ docker stop xxx
$ docker start xxx
$ docker restart xxx
```

```
$ docker pause xxx
$ docker unpause xxx
```

```
$ docker kill xxx
```