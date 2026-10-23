import psycopg
from psycopg.rows import dict_row

dbPassword = open("postgres_password_donotgit.txt", 'r').readline()

with psycopg.connect(dbname='postgres', user='postgres', password=dbPassword, host='localhost') as connection:
  with connection.cursor(row_factory=dict_row) as cursor:
    cursor.execute("SELECT SUM(times_taken) AS total_courses_taken FROM schedule")
    for row in cursor.fetchall():
      print(row["total_courses_taken"])