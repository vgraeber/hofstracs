import math

def addPrimes(primes, n):
  for i in range(primes[-1], n):
    prime = True
    for p in primes:
      if ((i % p) == 0):
        prime = False
        break
    if prime: primes.append(i)
  return primes

def isPrime(n):
  l = math.sqrt(n)
  if ((l % 1) == 0): return False
  l = math.floor(l)
  nums = open("prime_numbers.txt", 'r+')
  pNums = nums.read()
  primeNums = pNums.split(',')
  primeNums.pop()
  primeNums = list(map(int, primeNums))
  if (l > primeNums[-1]):
    primeNums = addPrimes(primeNums, l)
    nums.seek(0)
    for i in primeNums: nums.write(str(i) + ',')
    nums.truncate()
  nums.close()
  for i in primeNums:
    if ((n % i) == 0): return False
  return True

print("167:",isPrime(167))
print("127:",isPrime(127))
print("27:",isPrime(27))
print("9:",isPrime(9))
