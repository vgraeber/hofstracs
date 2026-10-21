# Author: Vivian Graeber
# Date: 11/17/23
# Description: Document class file

import parseUtility

precis = 4

class Document():
  def __init__(self, docText):
    self.docText = docText
    self.vocab = self.getVocab(docText)
    self.avgWordsPerSent = self.getAvgWordsPerSent(docText)
    self.avgTypeOfWordsPerSent = self.getAvgTypeOfWordsPerSent(docText)
    self.avgPunctPerSent = self.getAvgPunctPerSent(docText)
    self.avgCharsPerWord = self.getAvgCharsPerWord(docText)
    self.avgCapsPerSent = self.getAvgCapsPerSent(docText)

  def __str__(self):
    dslp = []
    dslt = []
    for k, v in self.avgPunctPerSent.items():
      if (k != "'"):
        dslp.append("{} '{}'s".format(v, k))
      else:
        dslp.append('{} "{}"s'.format(v, k))
    for k, v in self.avgTypeOfWordsPerSent.items():
      dslt.append("{} '{}'s".format(v, k))
    dsp = ""
    dst = ""
    for i in range(len(dslp)):
      dsp += dslp[i]
      if (i < (len(dslp) - 2)):
        dsp += ", "
      elif (i == (len(dslp) - 2)):
        dsp += ", and "
    for i in range(len(dslt)):
      dst += dslt[i]
      if (i < (len(dslt) - 2)):
        dst += ", "
      elif (i == (len(dslt) - 2)):
        dst += ", and "
    return f"This document has {len(self.vocab)} unique words, with averages of {self.avgWordsPerSent} words per sentence, {dst}, {self.avgCharsPerWord} characters per word, {dsp} per sentence, and {self.avgCapsPerSent} capital letters per sentence."

  def getVocab(self, docText):
    vocab = parseUtility.getVocabFreqs(docText)
    return vocab

  def getAvgWordsPerSent(self, docText):
    awps = parseUtility.getWordsPerSent(docText)
    return round(awps, precis)

  def getAvgTypeOfWordsPerSent(self, docText):
    atwps = parseUtility.getTypeFreqs(docText)
    for k, v in atwps.items():
      atwps[k] = round(v, precis)
    return atwps

  def getAvgPunctPerSent(self, docText):
    aptps = parseUtility.getPunctPerSent(docText)
    for k, v in aptps.items():
      aptps[k] = round(v, precis)
    return aptps

  def getAvgCharsPerWord(self, docText):
    achpw = parseUtility.getCharsPerWord(docText)
    return round(achpw, precis)

  def getAvgCapsPerSent(self, docText):
    acps = parseUtility.getCapsPerSent(docText)
    return round(acps, precis)

def getDocInfo(fname):
  inputfile = open(fname, 'r')
  docText = inputfile.read().strip()
  inputfile.close()
  docinfo = Document(docText)
  return docinfo
