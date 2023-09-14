# Author: Vivian Graeber
# Date Created: 9/11/23
# Current Date: 9*14/23
# Description: Conversion functions for binary and decimal numbers

def convertDecToBin(n):
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
  if (n[-1] == 1):
    decN -= (2 ** exp)
  return decN

def checkMode(mode):
  pass = False
  while (not pass):
    if (mode == "bin"):
      pass = True
      return "binary"
    elif (mode == "dec"):
      pass = True
      return "decimal"
    else:
      print("Error: not a valid mode. Please enter either 'bin' for binary or 'dec' for decimal)

def checkBinNum(num):
  pass = False

def main():
  mode = input("Please input the type of number you wish to convert to : ")
  modeName = checkMode(mode)
  num = input("Please enter the number you wish to convert to " + modename + ": ")
  if (mode == "bin"):
    num = checkBinNUm(num)
  else:
    num = checkDecNum(num)
