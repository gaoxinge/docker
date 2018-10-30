- [go run go build go install 命令解释](https://blog.csdn.net/zyz770834013/article/details/78656985)
- [Deploying Go Web Apps With Docker](https://medium.com/@shijuvar/deploying-go-web-apps-with-docker-1b7561b36f53)
- [Deploying Go servers with Docker](https://blog.golang.org/docker)

```
> docker build -t golang-docker .
> docker run -p 3000:8000 --name goweb --rm golang-docker
```