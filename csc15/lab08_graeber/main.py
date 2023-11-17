# Author: Vivian Graeber
# Date: 11/15/23
# Description: lab 8

from docclass import getDocInfo
from pathlib import Path
import os

def getFileNames():
  path = Path(__file__).parent / "data"
  return os.listdir(path)

def getAllDocInfo():
  fnames = getFileNames()
  allDocInfo = []
  path = Path(__file__).parent / "data"
  for fname in fnames:
    filename = str(path) + '/' + fname
    allDocInfo.append(getDocInfo(filename))
  return allDocInfo

def main():
  allDocInfo = getAllDocInfo()
  for doc in allDocInfo:
    print(doc)

main()
