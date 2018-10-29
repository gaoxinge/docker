- [Bootstrapping a Go application with Docker](https://medium.com/@rrgarciach/bootstrapping-a-go-application-with-docker-47f1d9071a2a)

```
> docker build -t my-golang-app-image .
> docker run -p 3030:3001 --name my-golang-app-run --rm my-golang-app-image
```