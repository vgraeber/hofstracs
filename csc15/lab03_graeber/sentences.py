# Author: Vivian Graeber
# Date: 9/21/23
# Description: lab 3 work problem 4

def countSents(para):
  endSent = ['.', '!', '?']
  sentCount = 0
  for i in range(len(para)):
    if ((para[i] in endSent) and (para[i - 1] not in endSent)):
      sentCount += 1
  return sentCount

def checkInput(para):
  endSent = ['.', '!', '?']
  while (para[-1] not in endSent):
    print("Error: invalid input. You did not end your paragraph.")
    para = input("Please enter a paragraph: ")
  return para

def main():
  para = input("Please enter a paragraph: ")
  para = checkInput(para)
  sentCount = countSents(para)
  ending = "sentences."
  if (sentCount == 1): ending = "sentence."
  print("The paragrapoh you entered has", sentCount, ending)

main()
