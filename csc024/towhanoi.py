# Author: Vivian Graeber
# Date: 03/27/24
# Description: Python program for generating moves for towers of hanoi

def intcheck(n):
  try:
    n = int(n)
    return ((n % 1) == 0)
  except ValueError:
    return False

def getrepmoves(n):
  if ((n % 2) == 0):
    return [[1, 2], [2, 3], [3, 1]]
  else:
    return [[1, 3], [3, 2], [2, 1]]

def totnummoves(n):
  return ((2 ** n) - 1)

def genmoveset(n):
  moves = []
  repmoves = getrepmoves(n)
  for i in range(totnummoves(n)):
    halfi = i // 2
    if ((i % 2) == 0):
      moves.append(repmoves[halfi % len(repmoves)])
    else:
      moves.append(genmoveset(n - 1)[halfi])
  return moves

def main():
  n = input("Please enter the number of disks: ")
  if ((not intcheck(n)) or (int(n) < 1)):
    print ("That's not a valid input. Please enter a natural number.")
  else:
    n = int(n)
    moves = genmoveset(n)
    print ("Moves: ", moves)
    print ("Num of moves: ", len(moves))

main()
