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
# Name(s)            Date         Comment
# Vivian             11/09/2023   Completed all parsing and frequency code
# Vivian and Justin  11/13/2023   Created the table output

from parseUtility import getWordFreqs
from pathlib import Path
from collections import Counter
import os

def getFileNames():
  path = path = Path(__file__).parent / "data"
  return os.listdir(path)

def getAllWordFreqs():
  fnames = getFileNames()
  wordFreqs = []
  path = Path(__file__).parent / "data"
  for fname in fnames:
    filename = str(path) + '/' + fname
    wordFreqs.append(Counter(getWordFreqs(filename)))
  return wordFreqs

def getTermFreqs(allWordFreqs):
  termFreqs = Counter({})
  for dict in allWordFreqs:
    termFreqs += dict
  return termFreqs

def getFirstColumnSpaces(termFreqs):
  lenlongestword = len("vocabulary")
  for word in sorted(termFreqs):
    if (len(word) > lenlongestword):
      lenlongestword = len(word)
  return lenlongestword

def printTable(termFreqs, allWordFreqs):
  buffer = "  " # buffer so that the columns are not directly next to each other
  nl = "\n"
  fclen = getFirstColumnSpaces(termFreqs) # gets the number of spaces needed for the first column so that no worda are cut off
  sclen = len("term freq")
  fnames = getFileNames()
  headerlens = [] # following 2 lines get the length of each file name for proper formatting
  for fname in fnames:
    headerlens.append(len(fname))
  table = "Vocabulary".ljust(fclen) + buffer + "Term Freq" # starts off the header for the table with the necessary formatting
  for fname in fnames:
    table += buffer
    table += fname
  table += nl
  words = sorted(termFreqs) # gets the list of all words in alphabetical order
  for word in words:
    table += word.ljust(fclen)
    table += buffer
    table += str(termFreqs.get(word)).center(sclen)
    for c in range(len(fnames)):
      table += buffer
      num = allWordFreqs[c].get(word) # gets the number of times the word appears in the given file
      if (num is None): # changes the formatting if the word does not appear in the file
        num = 0
      table += str(num).center(headerlens[c])
    table += nl
  print(table)

def main():
  allWordFreqs = getAllWordFreqs()
  termFreqs = getTermFreqs(allWordFreqs)
  printTable(termFreqs, allWordFreqs)

main()
