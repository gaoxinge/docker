## command

```
$ mkdir gitlab
$ cd gitlab
$ mkdir config
$ mkdir logs
$ mkdir data

$ sudo docker pull gitlab/gitlab-ce
$ sudo docker run -d -p 443:443 -p 80:80 -p 22:22 --hostname gitlab.example.com --name gitlab -v ~/gitlab/config:/etc/gitlab -v ~/gitlab/logs:/var/log/gitlab -v ~/gitlab/data:/var/opt/gitlab gitlab/gitlab-ce
```

## reference

- [GitLab Docker images](https://docs.gitlab.com/omnibus/docker/)
- [Based On Closed Issue At: #1601](https://gitlab.com/gitlab-org/omnibus-gitlab/issues/2280)
- [正确使用 Docker 搭建 GitLab 只要半分钟](https://zhuanlan.zhihu.com/p/49499229)
- [GitLab安装、使用教程（Docker版）](https://zhuanlan.zhihu.com/p/33592623)