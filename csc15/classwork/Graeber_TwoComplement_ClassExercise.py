# Author: Vivian Graeber
# Date: 9/11/23

def toBin(n):
  binN = []
  while (n != 0):
    binN.append(n % 2)
    n = n // 2
  binN.reverse()
  binN.insert(0, 0)
  return binN

def toDec(n, type):
  decN = 0
  exp = 0
  n.reverse()
  if (type == "bin"):
    for i in n:
      if (i == 1): decN += (2 ** exp)
      exp += 1
    return decN
  elif (type == "twosComp"):
    for i in range(len(n) - 1):
      if (i == 1): decN += (2 ** exp)
      exp += 1
    decN -= (2 ** exp)
    return decN
  else:
    return null

def toTwosComp(n):
  twosCompN = toBin(abs(n))
  for i in range(len(twosCompN)):
    if (twosCompN[i] == 1): twosCompN[i] = 0
    else: twosCompN[i] = 1
  added = False
  i = -1
  while (not added):
    if (twosCompN[i] == 1):
      twosCompN[i] = 0
      i -= 1
    else:
      twosCompN[i] = 1
      added = True
  return twosCompN

print(toBin(31))
print(toBin(32))
print(toDec(toBin(32), "bin"))
print(toTwosComp(-6))
print(toDec(toTwosComp(-6), "twosComp"))
