FROM tiangolo/uwsgi-nginx-flask:python2.7

COPY requirements.txt /tmp/
RUN pip install -U pip
RUN pip install -r /tmp/requirements.txt

COPY ./app /app

ENV MESSAGE "hello from Docker"