import sys
import psycopg
from psycopg.rows import dict_row
from psycopg import sql
    
def getUid(cursor, uinname):
  cursor.execute("SELECT id FROM users WHERE username = %s", (uinname,))
  for row in cursor.fetchall():
    return row["id"]

def getFollows(cursor, uid):
  cursor.execute("SELECT following_id FROM follows WHERE user_id = %s", (uid,))
  following = (uid,)
  for row in cursor.fetchall():
    following = following + (row['following_id'],)
  return following

def getFeed(cursor, following):
  query = sql.SQL("SELECT id, posted_by_id, post, timestamp FROM posts WHERE posted_by_id IN ({}) ORDER BY timestamp DESC").format(sql.SQL(', ').join(sql.Placeholder() * len(following)))
  cursor.execute(query, following)
  feed = []
  for row in cursor.fetchall():
    cursor.execute("SELECT commented_by_id, comment, timestamp FROM comments WHERE under_post_id = %s ORDER BY timestamp DESC", (row["id"],))
    comments = []
    for row2 in cursor.fetchall():
      comments.append(row2)
    row["comments"] = comments
    feed.append(row)
  return feed

def getUsername(cursor, uid):
  return cursor.execute("SELECT username from users WHERE id = %s", (uid,)).fetchone()["username"]

def formatFeed(cursor, feed):
  formattedFeed = []
  for post in feed:
    posterUsername = getUsername(cursor, post["posted_by_id"])
    formattedPost = "\n" + posterUsername + "\n" + post["timestamp"].strftime("%a %b %d, %Y %I:%M%p") + "\n\n" + post["post"] + "\n-"
    for comment in post["comments"]:
      commentUsername = getUsername(cursor, comment["commented_by_id"])
      formattedPost += "\n\t" + commentUsername + "\n\t" + comment["timestamp"].strftime("%a %b %d, %Y %I:%M%p") + "\n\n\t" + comment["comment"] + "\n"
    formattedPost += "-"
    formattedFeed.append(formattedPost)
  return formattedFeed

def printformattedFeed(formattedFeed):
  for formattedPost in formattedFeed:
    print(formattedPost)

def main():
  if (len(sys.argv) < 2):
    print("This file requires an additional argument to run. Please add a username after the file in this format: [file] [username]")
  else:
    dbPassword = open("postgres_password_donotgit.txt", 'r').readline()
    uinname = sys.argv[1]
    with psycopg.connect(dbname='lab3', user='postgres', password=dbPassword, host='localhost') as connection:
      with connection.cursor(row_factory=dict_row) as cursor:
        uid = getUid(cursor, uinname)
        if (uid is None):
          print("User not found.")
        else:
          following = getFollows(cursor, uid)
          feed = getFeed(cursor, following)
          if (not feed):
            print("Your feed is empty. Try following some people or making some posts.")
          else:
            formattedFeed = formatFeed(cursor, feed)
            printformattedFeed(formattedFeed)

main()