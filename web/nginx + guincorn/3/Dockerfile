FROM python:2

RUN pip install gunicorn

COPY . .

CMD ["gunicorn", "test:app", "--workers, "2", "--bind", "0.0.0.0:5000"]