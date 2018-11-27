FROM ubuntu

RUN apt-get update
RUN apt-get install -y nginx

RUN groupadd www        # 添加组www
RUN useradd -g www www  # 添加用户www到组www

COPY nginx.conf /etc/nginx/nginx.conf

RUN nginx -t            # 测试nginx.conf有没有语法问题

CMD ["nginx", "-g", "daemon off;"]