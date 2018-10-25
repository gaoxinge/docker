FROM python:2

RUN apt-get update
RUN apt-get install -y nginx supervisor

# requirements, app
COPY app.py /deploy/
COPY requirements.txt /deploy/
RUN pip install -r /deploy/requirements.txt

# app

# nginx
RUN rm /etc/nginx/sites-enabled/default
COPY flask.conf /etc/nginx/sites-available/
RUN ln -s /etc/nginx/sites-available/flask.conf /etc/nginx/sites-enabled/flask.conf
RUN echo "daemon off;" >> /etc/nginx/nginx.conf

# supervisor
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY gunicorn.conf /etc/supervisor/conf.d/gunicorn.conf

CMD ["/usr/bin/supervisord"]