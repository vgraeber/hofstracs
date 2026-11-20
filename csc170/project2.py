import time
import psycopg
from psycopg.rows import dict_row
from datetime import datetime, timezone

def main():
  dbPassword = open("postgres_password_donotgit.txt", 'r').readline()
  secsBetweenPosts = 60
  uid = 4
  with psycopg.connect(dbname='lab3', user='postgres', password=dbPassword, host='localhost') as connection:
    with connection.cursor(row_factory=dict_row) as cursor:
      while True:
        post = "test"
        curTime = datetime.now(timezone.utc)
        try: 
          cursor.execute("INSERT INTO posts (posted_by_id, post, timestamp) VALUES (%s, %s, %s)", (uid, post, curTime,))
          connection.commit()
        except psycopg.errors.UniqueViolation:
          print("Duplicate ID")
        time.sleep(secsBetweenPosts)

main()