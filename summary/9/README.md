## swarm

```
$ docker swarm init xxx
$ docker swarm join-token worker
$ docker swarm join-token manage
$ docker node ls
$ docker swarm leave -f
```

```
$ docker-machine create -d hyperv --hyperv-virtual-switch "Primary Virtual Switch" manager
$ docker-machine create -d hyperv --hyperv-virtual-switch "Primary Virtual Switch" worker

$ docker-machine ls
NAME      ACTIVE   DRIVER   STATE     URL                         SWARM   DOCKER     ERRORS
manager   -        hyperv   Running   tcp://172.18.170.241:2376           v18.09.0
worker    -        hyperv   Running   tcp://172.18.171.249:2376           v18.09.0

$ docker-machine ssh manager
docker@manager: $ docker swarm init --advertise-addr 172.18.170.241
docker@manager: $ exit

$ docker-machine ssh worker
docker@worker: $ docker swarm join --token token 172.18.170.241:2377
docker@worker: $ exit

$ docker-machine ssh manager
docker@manager: $ docker node ls
```

```
$ docker-machine ssh manager
docker@manager: $ docker service create --name web_server httpd
docker@manager: $ docker service ls
docker@manager: $ docker service ps web_server
```

```
$ docker-machine ssh manager
docker@manager: $ docker service scale web_server=2
docker@manager: $ docker service ls
docker@manager: $ docker service ps web_server
```

```
$ docker-machine stop worker
$ docker-machine ssh manager
docker@manager: $ docker service ls
docker@manager: $ docker service ps web_server
```

```
$ docker-machine ssh manager
docker@manager: $ docker service create --name web_server --publish mode=host,published=8080,target=80 --replicas=2 httpd
```

### reference

- [Docker Swarm Cheatsheet](https://blog.programster.org/docker-swarm-cheatsheet)
- [Get Started, Part 4: Swarms](https://docs.docker.com/v17.09/get-started/part4/)
- [Limitations](https://docs.microsoft.com/en-us/virtualization/windowscontainers/manage-containers/swarm-mode#limitations)