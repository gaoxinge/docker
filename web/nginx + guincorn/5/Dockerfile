FROM python:2

RUN pip install gunicorn
RUN pip install django

COPY . /app/

CMD ["gunicorn", "-c", "/app/gunicorn.py", "HelloWorld.wsgi:application"]