import psycopg

with psycopg.connect("dbname='schedule' user='postgres'") as connection:
  with connection.cursor() as cursor:
    cursor.execute("SELECT * FROM schedule WHERE tuesday = 1")
    row = cursor.fetchone()