# gliderlabs/consul-server

## command

```
# http://10.0.75.1:8500 or http://localhost:8500
$ docker run -d -p 8400:8400 -p 8500:8500 -p 8600:53/udp --name node1 gliderlabs/consul-server -bootstrap -advertise 10.0.75.1
```

```
# http://10.0.75.1:8500 or http://localhost:8500
$ docker run -d -p 8400:8400 -p 8500:8500 -p 8600:53/udp --name node1 gliderlabs/consul-server -bootstrap -advertise 127.0.0.1
```