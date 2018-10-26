FROM python:2

RUN pip install uwsgi

COPY . /app/

CMD ["uwsgi", "--http", ":9090", "--wsgi-file", "/app/foobar.py", "--master", "--processes", "4", "--threads", "2"]