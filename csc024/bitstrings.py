# Author: Vivian Graeber
# Date: 04/29/24
# Description: Python program for generating bitstrings with the string '11'

import itertools

def intcheck(n):
  try:
    n = int(n)
    return ((n % 1) == 0)
  except ValueError:
    return False

def genbitstrings(n):
  bitstrings = list(itertools.product([0, 1], repeat=n))
  return bitstrings

def filterbitstrings(bitstrings):
  filteredbitstrings = []
  for string in bitstrings:
    for n in range(len(string) - 1):
      if ((string[n] == 1) and (string[n + 1] == 1)):
        filteredbitstrings.append(string)
        break
  return filteredbitstrings

def main():
  n = input("Please enter the number of disks: ")
  while ((not intcheck(n)) or (int(n) < 1)):
    print ("That's not a valid input. Please enter a natural number.")
    n = input("Please enter the number of disks: ")
  n = int(n)
  bitstrings = genbitstrings(n)
  filteredbitstrings = filterbitstrings(bitstrings)
  print ("Val Strings: ", filteredbitstrings)
  print ("Num of Val Strings: ", len(filteredbitstrings))

main()
