import time
import turtle as tr

def checkLen(t):
  if (len(t) == 1): return ('0' + t)
  return t

def clock(form):
  limHrs = 24
  limMins = 60
  limSecs = 60
  append = ""
  if (form == "24hr"): pass
  elif (form == "12hr"): append = " AM"
  else: print("Invalid time format")
  for i in range(limHrs):
    hrs = str(i)
    if (form == "12hr"):
      if (i > 12):
        append = " PM"
        hrs = str(i - 12)
      elif (i == 0): hrs = '12'
    hrs = checkLen(hrs)
    for j in range(limMins):
      mins = str(j)
      mins = checkLen(mins)
      for k in range(limSecs):
        secs = str(k)
        secs = checkLen(secs)
        curTime = (hrs + ':' + mins + ':' + secs + append)
        tr.write(curTime, align="center", font=("Times New Roman", 50, "bold"))
        tr.hideturtle()
        time.sleep(1)
        tr.clear()

clock("12hr")