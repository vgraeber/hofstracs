# Author: Vivian Graeber
# Date: 11/15/23
# Description: Python program for getting the gcd of two numbers

import calcPrimality as prime
import math

def intCheck(num):
  if ((num % 1) == 0):
    return True
  return False

def gcd(snum, lnum):
  if (snum > lnum):
    snum, lnum = lnum, snum
  elif (snum == lnum):
    return snum
  if prime.isPrime(snum) or prime.isPrime(lnum):
    return 1
  elif (snum == 0):
    return lnum
  elif intCheck(lnum / snum):
    return snum
  maxDiv = math.sqrt(snum)
  pNums = prime.getPrimes(maxDiv)
  gcd = 1
  for n in pNums:
    sdiv = snum / n
    ldiv = lnum / n
    if intCheck(sdiv) and intCheck(ldiv):
      sdiv /= n
      ldiv /= n
      while ((intCheck(sdiv) and intCheck(ldiv)) and (not prime.isPrime(sdiv)) and (not prime.isPrime(ldiv))):
        gcd *= n
      gcd *= n
  return gcd

def main():
  n1 = input("Please enter your first number: ")
  n1 = prime.check(n1)
  n2 = input("Please enter your second number: ")
  n2 = prime.check(n2)
  print("The gcd of " + str(n1) + " and " + str(n2) + " is " + str(gcd(n1, n2)))

main()
