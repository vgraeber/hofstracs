# Author: Vivian Graeber
# Date: 9/21923
# Description: lab3 work problem 2

def checkInput(i):
  lower = [ord('a'), ord('z')]
  upper = [ord('A'), ord('Z')]
  if ((lower[0] <= i) and (i <= lower[1])):
    return True
  elif ((upper[0] <= i) and (i <= upper[1])):
    return True
  return False

def countConsts(word):
  vowList = ['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']
  constCount = 0
  for i in word:
    if (checkInput(ord(i)) and (i not in vowList)):
      constCount += 1
  return constCount

def main():
  word = input("Please enter a word: ")
  constCount = countConsts(word)
  ending = "consonants."
  if (constCount == 1):
    ending = "consonant."
  print("The word you entered has", constCount, ending)

main()
