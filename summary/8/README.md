## command

```
> docker-machine create -d hyperv --hyperv-virtual-switch "Primary Virtual Switch" manager
> docker-machine ls
> docker-machine rm manager
```

## reference

- [Docker Machine Overview](https://docs.docker.com/machine/overview/)
- [Microsoft Hyper-V](https://docs.docker.com/machine/drivers/hyper-v/)
- [一步步创建第一个Docker App —— 2. 创建 Docker化 主机](https://www.cnblogs.com/zhxshseu/p/011245978fc443fbc6f273ad7e22ed7c.html)
- [always re-fetch boot2docker, it said 'Default Boot2Docker ISO is out-of-date'](https://github.com/docker/machine/issues/4058)
- [how to let docker-machine use a local boot2docker.iso](https://stackoverflow.com/questions/34717111/how-to-let-docker-machine-use-a-local-boot2docker-iso)