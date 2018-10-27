FROM python:2

RUN apt-get update
RUN apt-get install -y nginx supervisor

RUN pip install django
RUN pip install gunicorn

RUN rm /etc/nginx/sites-enabled/default
COPY django.conf /etc/nginx/sites-available/
RUN ln -s /etc/nginx/sites-available/django.conf /etc/nginx/sites-enabled/django.conf
RUN echo "daemon off;" >> /etc/nginx/nginx.conf

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

COPY . /app/

CMD ["supervisord"]