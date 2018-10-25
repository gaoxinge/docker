- [用docker部署flask+gunicorn+nginx](https://www.cnblogs.com/xuanmanstein/p/7692256.html)

```
> docker build -t danriti/nginx-gunicorn-flask .
> docker run -d -p 80:80 danriti/nginx-gunicorn-flask
```

- localhost:80
- 10.0.75.1:80