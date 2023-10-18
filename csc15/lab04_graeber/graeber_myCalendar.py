# Author: Vivian Graeber
# Date: 10/05/23
# Description: Lab 4 work

import math

def readInput(i, t):
  if (t == "mon"):
    while ((not i.isnumeric()) or (int(i) not in range(1, 13))):
      print("Invalid input. Please enter a month as a number between 1 and 12.")
      i = input("Month: ")
    return int(i)
  elif (t == "year"):
    while ((not i.isnumeric()) or (int(i) == 0)):
      print("Invalid input. Please enter a full year (ex. 2011).")
      i = input("Year: ")
    return i
  else: return "Internal code error"

def isLeapYear(y):
  if (((y % 100) == 0) and ((y % 400) != 0)): return False
  elif ((y % 4) == 0): return True
  else: return False

def getNumberOfDaysInMonth(mon, y):
  if (mon == 2):
    if isLeapYear(y): return 29
    else: return 28
  elif (mon in [4, 6, 9, 11]): return 30
  else: return 31

def getStartDay(mon, y):
  monAdj = mon - 2
  if (monAdj < 1): monAdj += 12
  if (len(y) < 4): y = ('0' * (4 - len(y))) + y
  cen = int(y[:-2])
  year = int(y[-2:])
  if (mon < 3): year -= 1
  d = (1 + math.floor((2.6 * monAdj) - .2) - (2 * cen) + year + math.floor(year / 4) + math.floor(cen / 4)) % 7
  return (d + 1)

def getMonthName(mon):
  months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
  return months[mon - 1]

def printMonthTitle(mon, y):
  name = getMonthName(mon)
  title = name + "  " + y
  title = title.center(28)
  print("\n" + title)
  print("----------------------------")
  print(" Sun Mon Tue Wed Thu Fri Sat" + "\n")

def printMonthBody(mon, y):
  start = getStartDay(mon, y)
  days = getNumberOfDaysInMonth(mon, int(y))
  s = "  "
  month = ((start - 1) * (s * 2))
  rows = (start + days) % 7
  endRow = 7 - (start - 1)
  if (endRow == 7): endRow = 0
  for i in range(days):
    d = str(i + 1)
    if (len(d) == 1): d = '0' + d
    month += s + d
    if ((int(d) % 7) == endRow): month += "\n"
  print(month + "\n")

def printMonth(mon, y):
  printMonthTitle(mon, y)
  printMonthBody(mon, y)

def printCalendar():
  year = input("Please enter a full year (ex. 2011): ")
  year = readInput(year, "year")
  mon = input("Please enter a month as a number between 1 and 12: ")
  mon = readInput(mon, "mon")
  printMonth(mon, year)  

printCalendar()
