FROM python:2

RUN apt-get update
RUN apt-get install -y nginx supervisor

ADD src/. /src/

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

CMD ["supervisord"]