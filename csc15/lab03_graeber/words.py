# Author: Vivian Graeber
# Date: 9/21/23
# Description: lab3 work problem 3

def countWords(sent):
  wordCount = 1
  for i in range(len(sent)):
    if ((sent[i] == ' ') and (sent[i - 1] != ' ')):
      wordCount += 1
  return wordCount

def checkInput(sent):
  endSent = ['.', '!', '?']
  while (sent[-1] not in endSent):
    print("Error: invalid input. You did not end your sentence.")
    sent = input("Please enter a sentence: ")
  return sent

def main():
  sent = input("Please enter a sentence: ")
  sent = checkInput(sent)
  wordCount = countWords(sent)
  ending = "words."
  if (wordCount == 1): ending = "word."
  print("The sentence you entered has", wordCount, ending)

main()
