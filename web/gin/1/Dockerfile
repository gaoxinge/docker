FROM golang

RUN go get github.com/gin-gonic/gin

COPY main.go /go/src/ginDocker/

WORKDIR /go/src/ginDocker 

RUN go build ginDocker

EXPOSE 7070

ENTRYPOINT ./ginDocker