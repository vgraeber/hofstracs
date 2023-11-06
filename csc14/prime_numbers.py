import math

def addPrimes(primes, n):
  for i in range(primes[-1], n):
    prime = True
    for p in primes:
      if ((i % p) == 0):
        prime = False
        break
    if prime:
      primes.append(i)
  return primes

def isPrime(n):
  l = math.sqrt(n)
  if ((l % 1) == 0):
    return False
  l = math.floor(l)
  nums = open("prime_numbers.txt", 'r+')
  pNums = nums.read()
  primeNums = pNums.split(',')
  primeNums.pop()
  primeNums = list(map(int, primeNums))
  if (l > primeNums[-1]):
    primeNums = addPrimes(primeNums, l)
    nums.seek(0)
    for i in primeNums:
      nums.write(str(i) + ',')
    nums.truncate()
  nums.close()
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

main()
