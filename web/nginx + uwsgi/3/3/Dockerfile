FROM python:2

RUN pip install uwsgi
RUN pip install django

COPY . /app/

CMD ["uwsgi", "/app/yourfile.ini"]