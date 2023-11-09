# Description Lab 7
# Create a Bag Of Words Model from speeches
# Implement function abstraction
#
# Team Members:
#
#
#
# Revision History
# -    -    -    -    -    -    -    -    -    -    -
# name      date        comment
# scl       11/4/2023   Initial code framework for students
#
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
