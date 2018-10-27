- [用docker部署flask+gunicorn+nginx](https://www.cnblogs.com/xuanmanstein/p/7692256.html)
- [Flask + Gunicorn + Nginx 部署](http://python.jobbole.com/87666/)

```
> docker build -t yy .
> docker run -d -p 8000:80 yy
```

- localhost:8000
- 10.0.75.1:8000