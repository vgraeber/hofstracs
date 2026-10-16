# Author: Vivian Graeber
# Date: 11/15/23
# Description: Python class practice

import random
from graeber_circleclass import Circle

def getrads(num):
  randrads = []
  for i in range(num):
    randrads.append(random.randint(0, 100))
  return randrads

def main():
  rads = getrads(3)
  circs = []
  for i in rads:
    circs.append(Circle(i))
  for i in circs:
    print(i)

main()
