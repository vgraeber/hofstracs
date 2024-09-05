# Author: Vivian Graeber
# Date: 12/06/23
# Description: Python time module classwork

import time

fibnums = [0, 1]

def getTime():
  return time.perf_counter()

def runFibonacci(num):
  if (num < len(fibnums)):
    return fibnums[num]
  else:
    for i in range(len(fibnums), (num + 1)):
      newfibnum = (runFibonacci(i - 1) + runFibonacci(i - 2))
      fibnums.append(newfibnum)
    return (fibnums[num])

def runAckermann(n1, n2):
  if (n1 == 0):
    return (n2 + 1)
  elif (n2 == 0):
    return runAckermann(n1 - 1, 1)
  else:
    return runAckermann(n1 - 1, runAckermann(n1, n2 - 1))

def calcTime(func):
  start = getTime()
  func
  end = getTime()
  return (end - start)

def main():
  i = 1000
  print (f"Fibonacci time for the {i}th Fibonacci number:", calcTime(runFibonacci(i)))
  print ("Fibonacci number:", fibnums[i], "\n")
  n1, n2 = 2, 7
  print (f"Ackermann time for {n1} and {n2}:", calcTime(runAckermann(n1, n2)))
  print ("Ackermann number:", runAckermann(n1, n2), "\n")

main()
