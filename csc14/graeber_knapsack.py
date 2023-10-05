import itertools, re

def anstr(s):
  return re.sub('[\W_]+', '', s)

def checkCont(cont):
  while ((cont != 'y') and (cont != 'n')):
    if (cont == "yes"): cont = 'y'
    elif (cont == "no"): cont = 'n'
    else:
      print("Invalid input. Please enter 'Y' or 'N'.")
      cont = input("Do you wish to continue running the program? (Y/N) ")
    cont = anstr(cont)
    cont.lower()
  return cont

def checkInput(x):
  if (x == ''): return False
  for i in x:
    if (not i.isnumeric()): return False
  return True

def checkMode(mode):
  while ((mode != "list") and (mode != "num")):
    if ((mode == "number") or (mode == 'n')): mode = "num"
    elif (mode == "l"): mode = "list"
    else:
      print("Invalid input. PLease enter 'list' or 'num'.")
      mode = input("Mode: ")
  return mode

def getList():
  print("Please enter the list of numbers you wish to use to try to sum up to your number.")
  print("Please enter 1 number at a time, and when you are done entering numbers, please enter 'end'.")
  end = False
  nums = []
  while (not end):
    x = input("Number: ")
    x = anstr(x)
    x = x.lower()
    while ((x != "end") and (not checkInput(x))):
      print("Invalid input. Please enter either a positive integer or 'end' to signify the end of the list.")
      x = input("Number: ")
      x = anstr(x)
      x = x.lower()
    if ((x == "end") and (len(nums) == 0)): print("Error. Cannot sum an empty list.")
    elif (x == "end"): end = True
    else: nums.append(int(x))
  return nums

def getNum():
  print("Please enter the number you wish to try to sum to.")
  num = input("Nunber: ")
  num = anstr(num)
  while (not checkInput(num)):
    print("Invalid input. Please enter a positive integer.")
    num = input("Number: ")
    num = anstr(num)
  return int(num)

def getInputs(num, nums):
  print("Please enter 'num' if you wish to start by entering the number you want to sum to, and 'list' if you wish to start by entering the list of numbers used to try to sum to the number")
  mode = input("Mode: ")
  mode = anstr(mode)
  mode.lower()
  mode = checkMode(mode)
  if (mode == "list"):
    nums = getList()
    num = getNum()
  else:
    num = getNum()
    nums = getList()
  return num, nums

def calcSum(x):
  s = 0
  for i in x: s += i
  return s

def knapsack(x, y):
  subSets = []
  for i in range(len(x)):
    subSets.append(list(itertools.combinations(x, i)))
  for i in range(len(subSets)):
    for j in range(len(subSets[i])):
      if (calcSum(subSets[i][j]) == y):
        print(subSets[i][j], y)
        return True
  return False

def main():
  print("You have started the knapsack program.")
  print("This program takes 2 inputs, a number, and a list of numbers to try to sum up to that number.")
  cont = input("Do you wish to continue running this program? (Y/N) ")
  cont = anstr(cont)
  cont = cont.lower()
  cont = checkCont(cont)
  if (cont == 'n'): exit()
  num = 0
  nums = []
  num, nums = getInputs(num, nums)
  print(knapsack(nums, num))

main()
