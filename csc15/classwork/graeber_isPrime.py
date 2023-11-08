# Author: Vivian Graeber
# Date: 10/09/23
# Descripton: Classwork for the day

import math
import random

# checks if a number is prime
def isAPrimeNumber(n):
  rtnum = math.sqrt(n)
  if ((rtn % 1) == 0):
    return False
  rtnum = math.floor(rtnum)
  if ((n % 2) == 0):
    return False
  for i in range(3, rtnum, 2):
    if (isAPrimeNumber(i)):
      if ((n % i) == 0):
         return False
  return True

# generates a list of integers between the min and max
def generateNumbers(lenList, minn, maxx):
  nums = []
  for i in range(lenList):
    nums.append([random.randint(minn, maxx)])
  return nums

def main():
  nums = generateNumbers(20, 1, 99)
  for i in range(len(nums)):
    nums[i].append(isAPrimeNumber(nums[i][0])) # appends the result of whether a number is prime to the list of integers
  print(nums)

main()
