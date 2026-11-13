import time
import psycopg
from psycopg.rows import dict_row
from datetime import datetime, timezone

def main():
  dbPassword = open("postgres_password_donotgit.txt", 'r').readline()
  secsBetweenPosts = 300
  uid = 4
  with psycopg.connect(dbname='lab3', user='postgres', password=dbPassword, host='localhost') as connection:
    with connection.cursor(row_factory=dict_row) as cursor:
      tableId = cursor.execute("SELECT max(id) from posts").fetchone()["max"]
      while True:
        tableId += 1
        post = "test"
        curTime = datetime.now(timezone.utc)
        cursor.execute("INSERT INTO posts (id, posted_by_id, post, timestamp) VALUES (%s, %s, %s, %s)", (tableId, uid, post, curTime,))
        connection.commit()
        cursor.execute("SELECT * FROM posts")
        for row in cursor.fetchall():
          print(row)
        time.sleep(secsBetweenPosts)

main()