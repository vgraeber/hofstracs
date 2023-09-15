# Author: Vivian Graeber
# Date Created: 9/12/23
# Current Date: 9/14/23
# Description: Conversion function for Twos Complement in addition to conversion functions for binary and decimal

def convertDecToBin(n):
  if (n < 0): return toTwosComp(n)
  binN = []
  while (n != 0):
    binN.append(n % 2)
    n //= 2
  binN.reverse()
  binN.insert(0, 0)
  return binN

def convertBinToDec(n):
  decN = 0
  exp = 0
  n.reverse()
  for i in range(len(n) - 1):
    if (n[i] == 1): decN += (2 ** exp)
    exp += 1
  if (n[-1] == 1): decN -= (2 ** exp)
  n.reverse()
  return decN

def checkNum(num):
  cont = False
  while (not cont):
    if isinstance(num, int): cont = True
    else:
      print("Error: Invalid input. Please enter an integer.")
      num = int(input("Please enter the number you wish to convert to decimal: "))
  return num

def toTwosComp(n):
  twosCompN = convertDecToBin(abs(n))
  for i in range(len(twosCompN)):
    if (twosCompN[i] == 1): twosCompN[i] = 0
    else: twosCompN[i] = 1
  added = False
  i = -1
  while (not added):
    if (twosCompN[i] == 1):
      twosCompN[i] = 0
      i -= 1
    else:
      twosCompN[i] = 1
      added = True
  return twosCompN

def main():
  num = int(input("Please enter the number you wish to convert to binary: "))
  num = checkNum(num)
  binN = convertDecToBin(num)
  decN = convertBinToDec(binN)
  print("The original number input:           ", num)
  print("That input in binary:                ", binN)
  print("The binary converted back to decimal:", decN)

main()
