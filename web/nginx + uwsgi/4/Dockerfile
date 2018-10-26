FROM python:2

RUN apt-get update
RUN apt-get install -y nginx supervisor

RUN pip install uwsgi
RUN pip install flask

COPY main.py /app/
COPY uwsgi.ini /app/

RUN rm /etc/nginx/sites-enabled/default
COPY flask.conf /etc/nginx/sites-available/
RUN ln -s /etc/nginx/sites-available/flask.conf /etc/nginx/sites-enabled/flask.conf
RUN echo "daemon off;" >> /etc/nginx/nginx.conf

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

CMD ["supervisord"]