import math

def isPrime(n):
  l = math.sqrt(n)
  if ((l % 1) == 0):
    return False
  l = math.floor(l)
  if ((n % 2) == 0):
    return False
  for i in range(3, l, 2):
    if (isPrime(i)):
      if ((n % i) == 0):
        return False
  return True

print("167:",isPrime(167))
print("127:",isPrime(127))
print("27:",isPrime(27))
print("9:",isPrime(9))
