FROM python:2

RUN pip install gunicorn
RUN pip install flask

COPY . .

CMD ["gunicorn", "-w", "4", "-b", "0.0.0.0:8080", "main:app"]