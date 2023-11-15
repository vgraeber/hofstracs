# Author: Vivian Graeber
# Date: 11/15/23
# Description: Python program for getting the gcd of two numbers

import calcPrimality as prime
import math

def gcd(snum, lnum):
  if (snum > lnum):
    snum, lnum = lnum, snum
  elif (snum == lnum):
    return snum
  if (prime.isPrime(snum) or prime.isPrime(lnum)):
    return 1
  elif (snum == 0):
    return lnum
  elif (((lnum / snum) % 1) == 0):
    return snum
  maxDiv = math.sqrt(snum)
  pNums = prime.getPrimes(maxDiv)
  for n in pNums:
    if ((((snum / n) % 1) == 0) and (((lnum / n) % 1) == 0)):
      if ((snum / n) > n):
        return int(snum / n)
      else:
        return n
  return 1

def main():
  n1 = input("Please enter your first number: ")
  n1 = prime.check(n1)
  n2 = input("Please enter your second number: ")
  n2 = prime.check(n2)
  print("The gcd of " + str(n1) + " and " + str(n2) + " is " + str(gcd(n1, n2)))

main()
