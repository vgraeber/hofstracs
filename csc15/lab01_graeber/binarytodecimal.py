# Author: Vivian Graeber
# Date Created: 9/11/23
# Current Date: 9/14/23
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
    if (n[i] == 1):
      decN += (2 ** exp)
    exp += 1
  if (n[-1] == 1):
    decN -= (2 ** exp)
  n.reverse()
  return decN

def checkNum(num):
  while not num.isnumeric():
    print("Error: Invalid input. Please enter a whole number.")
    num = input("Please enter the number you wish to convert to decimal: ")
  return int(num)

def main():
  num = int(input("Please enter the number you wish to convert to binary: "))
  num = checkNum(num)
  binN = convertDecToBin(num)
  decN = convertBinToDec(binN)
  print("The original number input:            ", num)
  print("That input in binary:                 ", binN)
  print("The binary converted back to decimal: ", decN)

main()
