# Description Lab 7
# Create a Bag Of Words Model from speeches
# Implement function abstraction
#
# Team Members:
# Vivian Graeber
# Justin Telhu
#
# Revision History
# -    -    -    -    -    -    -    -    -    -    -
# Name      Date         Comment
# Vivian    11/09/2023   Completed all parsing and frequency code
#

from parseUtility import getWordFreqs
from collections import Counter
import os

def getAllWordFreqs():
  fnames = os.listdir("./data")
  wordFreqs = []
  for fname in fnames:
    filename = "./data/" + fname
    wordFreqs.append(getWordFreqs(filename))
  return wordFreqs

def getTermFreqs(allWordFreqs):
  termFreqs = Counter({})
  for dict in allWordFreqs:
    termFreqs += dict
  return termFreqs

def main():
  allWordFreqs = getAllWordFreqs()
  termFreqs = getTermFreqs(allWordFreqs)
  print(termFreqs)

# - - run main() - -
s = main()
