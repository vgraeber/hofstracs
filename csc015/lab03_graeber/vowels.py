# Author: Vivian Graeber
# Date: 9/21923
# Description: lab3 work problem 1

def countVowels(word):
  vowList = ['a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U']
  vowCount = 0
  for i in word:
    if (i in vowList):
      vowCount += 1
  return vowCount

def main():
  word = input("Please enter a word: ")
  vowCount = countVowels(word)
  ending = "vowels."
  if (vowCount == 1):
    ending = "vowel."
  print("The word you entered has", vowCount, ending)

main()
