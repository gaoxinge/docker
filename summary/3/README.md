## 1

```
$ docker run -it ubuntu
root@e2e75ceff1e6:/# apt-get update && apt-get install -y vim
root@e2e75ceff1e6:/# exit
$ docker ps -a
$ docker commit e2e75ceff1e6 ubuntu-with-vi
$ docker images
$ docker history ubuntu-with-vi
```

## 2

```
$ docker build -t ubuntu-with-vi .
$ docker history ubuntu-with-vi
```