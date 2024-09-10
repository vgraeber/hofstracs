# Author: Vivian Graeber
# Date: 9/14/23
# Description: lab2 work question 1

def isNum(uInput):
  numRange = [ord('0'), ord('9')]
  for i in uInput:
    if ((ord(i) < numRange[0]) or (numRange[1] < ord(i))):
      return False
  return True

def getInputs():
  inputs = []
  stopped = False
  while (not stopped):
    n = input("Enter a number or [ 0 ] to exit: ")
    if (isNum(n)):
      if (int(n) == 0):
        stopped = True
      else:
        inputs.append(int(n))
    else:
      print(n, "was not a valid input. Please try again.")
  print("Your input numbers are:", inputs)
  return inputs

def getAvg(inputs):
  sum = 0
  for i in inputs:
    sum += i
  avg = (sum / len(inputs))
  print("Sum:", sum)
  print("Avg:", avg)

def main():
  nums = getInputs()
  getAvg(nums)

main()
