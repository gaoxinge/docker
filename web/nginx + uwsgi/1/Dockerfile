FROM python:2

COPY requirements.txt /webapp/

RUN pip install uwsgi
RUN pip install -r /webapp/requirements.txt
 
ADD . /webapp

ENV HOME /webapp
WORKDIR /webapp

EXPOSE 8000

ENTRYPOINT ["uwsgi", "--http", "0.0.0.0:8000", "--module", "app:app", "--processes", "1", "--threads", "8"]