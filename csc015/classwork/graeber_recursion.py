# Author: Vivian Graeber
# Date: 12/04/23
# Description: Class exercise 6

import random

def genRandNums(numRandNums):
  randNums = []
  for i in range(numRandNums):
    randNums.append(random.randint(1,999))
  return randNums

def getSum(num):
  if (num < 0):
    return "There are no non-negative integers less than this number."
  elif (num == 0):
    return 0
  return (num + getSum(num - 1))

def getFactorial(num):
  if (num < 0):
    return "The factorial for this is not defined."
  elif (num <= 1):
    return 1
  return (num * getFactorial(num - 1))

def getGCD(n1, n2):
  if (n1 == 0):
    return n2
  return getGCD(n2 % n1, n1)

def getLCM(n1, n2):
  return ((n1 * n2) // getGCD(n1, n2))

def main():
  snum = genRandNums(1)[0]
  print("sum of all non-negative numbers less than or equal to " + str(snum))
  print(getSum(snum))
  fnum = genRandNums(1)[0]
  print("factorial of " + str(fnum))
  print(getFactorial(fnum))
  gnums = genRandNums(2)
  gn1, gn2 = gnums[0], gnums[1]
  if (gn2 < gn1):
    gn1, gn2 = gn2, gn1
  print("gcd of " + str(gn1) + " and " + str(gn2))
  print(getGCD(gn1, gn2))
  lnums = genRandNums(2)
  ln1, ln2 = lnums[0], lnums[1]
  if (ln2 < ln1):
    ln1, ln2 = ln2, ln1
  print("lcm of " + str(ln1) + " and " + str(ln2))
  print(getLCM(ln1, ln2))

main()
