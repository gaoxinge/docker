# Dockerfile

## best practice

- [Best practices for writing Dockerfiles](https://docs.docker.com/v17.09/engine/userguide/eng-image/dockerfile_best-practices/)

## RUN, CMD, ENTRYPOINT

### `docker run`

```
# /bin/sh -c bash
> docker run -it ubuntu bash
```

### `CMD [executable, param...]`

- Dockfile只执行最后一个
- `docker run`只执行外面的命令（如果有）

### `CMD [param...]`

```dockerfile
ENTRYPOINT ["/bin/echo", "Hello"]
CMD ["world"]
```

### reference

- [#!/bin/bash和#!/bin/sh的区别](https://www.jianshu.com/p/070bd0b4855f)
- [RUN vs CMD vs ENTRYPOINT - 每天5分钟玩转 Docker 容器技术（17）](https://www.ibm.com/developerworks/community/blogs/132cfa78-44b0-4376-85d0-d3096cd30d3f/entry/RUN_vs_CMD_vs_ENTRYPOINT_%E6%AF%8F%E5%A4%A95%E5%88%86%E9%92%9F%E7%8E%A9%E8%BD%AC_Docker_%E5%AE%B9%E5%99%A8%E6%8A%80%E6%9C%AF_17?lang=en)
- [Dockerfile RUN，CMD，ENTRYPOINT命令区别](https://www.jianshu.com/p/f0a0f6a43907)