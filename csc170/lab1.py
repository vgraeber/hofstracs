import psycopg
from psycopg.rows import dict_row

dbPassword = open("postgres_password_donotgit.txt", 'r').readline()
hostIP = open("host_ip_donotgit.txt", 'r').readline()

with psycopg.connect(dbname='postgres', user='postgres', password=dbPassword, host=hostIP) as connection:
  with connection.cursor(row_factory=dict_row) as cursor:
    cursor.execute("SELECT * FROM schedule ORDER BY course_name")
    for row in cursor.fetchall():
      print(row['course_name'])