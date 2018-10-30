FROM golang

WORKDIR /go/src/m

COPY . .

RUN go build src/main.go

CMD ["./main"]