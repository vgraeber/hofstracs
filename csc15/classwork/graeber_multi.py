# Author: Vivian Graeber
# Date: 09/27/23
# Descripton: Multiplication table

def multiTable():
  for i in range(1, 13):
    for j in range(1, 13):
      mult = str(i * j)
      mult = ((3 - len(mult)) * '0') + mult
      print(mult, end='\t'.expandtabs(3))
    print('\n')

multiTable()
