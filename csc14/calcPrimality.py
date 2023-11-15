import math

def addPrimes(primes, n):
  for i in range(primes[-1], (n + 1)):
    prime = True
    for p in primes:
      if ((i % p) == 0):
        prime = False
        break
    if prime:
      primes.append(i)
  return primes

def getPrimes(maxDiv):
  nums = open("primes.txt", 'r+')
  pNums = nums.read()
  primeNums = pNums.split(',')
  primeNums.pop()
  primeNums = list(map(int, primeNums))
  if (maxDiv > primeNums[-1]):
    primeNums = addPrimes(primeNums, maxDiv)
    nums.seek(0)
    for i in primeNums:
      nums.write(str(i) + ',')
    nums.truncate()
  nums.close()
  return primeNums

def isPrime(n):
  maxDiv = math.sqrt(n)
  if ((maxDiv % 1) == 0):
    return False
  maxDiv = math.floor(maxDiv)
  primeNums = getPrimes(maxDiv)
  for i in primeNums:
    if ((n % i) == 0):
      return False
  return True

def check(ui):
  while not ui.isnumeric():
    print("Invalid input.")
    ui = input("Please enter a number: ")
  return int(ui)

def main():
  ui = input("Please enter a number: ")
  ui = check(ui)
  print(str(ui) + " is prime: " + str(isPrime(ui)))

if __name__ == "__main__":
  main()
