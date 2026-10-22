# Author: Vivian Graeber
# Date: 09/27/23
# Descripton: Subtraction test

import random

Lnum = random.randint(0, 9)
Snum = random.randint(0, 9)
if (Lnum < Snum):
  Lnum, Snum = Snum, Lnum
ques = "What is " + str(Lnum) + " - " + str(Snum) + '?'
answer = Lnum - Snum

def checkAns(ans):
  while (ans != answer):
    print("Wrong answer. Please try again.", ques)
    ans = int(input("Answer: "))
  return ans

def main():
  print(ques)
  ans = int(input("Answer: "))
  checkAns(ans)
  print("You got it!")

main()
