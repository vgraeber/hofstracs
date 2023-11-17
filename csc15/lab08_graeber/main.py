# Author: Vivian Graeber
# Date: 11/15/23
# Description: lab 8

from parseUtility import Document
import os

def getFileNames():
  return os.listdir("./data")

def getAllDocInfo():
  fnames = getFileNames()
  allDocInfo = []
  for fname in fnames:
    filename = "./data/" + fname
    allDocInfo.append(Document(filename))
  return allDocInfo

def main():
  allDocInfo = getAllDocInfo()
  for doc in range(len(allDocInfo)):
    print(getFileNames()[doc])
    print(allDocInfo[doc]["avgWordsPerSent"])
    print(allDocInfo[doc]["avgPunctPerSent"])

main()
