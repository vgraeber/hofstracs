# Author: Vivian Graeber
# Date: 11/17/23
# Description: Document class file
# base code for average words per sentence copied from lab 3

import vocabUtil

class Document(self, docText):
  self.docText = docText
  self.vocab = self.getVocab(docText)
  self.avgWordsPerSent = self.getAvgWordsPerSent(docText)
  self.avgPunctPerSent = self.getAvgPunctPersent(docText)
  self.avgCharsPerWord = self.getAvgCharsPerWord(docText)
  self.avgCapsPerSent = self.getAvgCapsPerSent(docText)

  def getVocab(self, docText): # returns the dictionary of the word frequency for the given file
    vocab = vocabUtil.getWordFreqs(docText)
    return vocab

def cleanSentsGen(ui):
  endSent = ['.', '!', '?']
  titles = ["dr.", "esq.", "hon.", "jr.", "mr.", "mrs.", "ms.", "mx.", "messrs.", "mmes.", "msgr.", "rt.", "sr.", "st.", "ald.", "sen.", "gen.", "rep.", "gov.", "pres.", "col.", "lt.", "insp.", "asst.", "assoc.", "rev."]
  degrees = ["b.a.", "b.s.", "ph.d.", "m.d.", "b.f.a.", "b.s.b.a.", "b.s.ed.", "b.s.n.", "b.s.w.", "m.a.", "m.a.t.", "m.c.s.", "m.ed.", "m.f.a.", "m.h.s.a.", "m.l.s.", "m.mus.", "m.s.m.", "m.s.s.", "m.s.", "ed.d.", "d.p.c.", "ed.s.", "b.s.e.", "b.m.", "b.s.e.e.", "d.a.", "d.b.a.", "d.d.s.", "j.d.", "m.b.a.", "ed.m.", "d.m.l.", "d.min", "d.p.t.", "m.div.", "m.m.", "m.p.a.", "m.phil.", "m.s.a.", "m.s.e.e.", "m.s.l.i.s.", "m.s.p.t.", "m.th.", "r.n.", "s.t.m.", "th.d."]
  ui = ui.lower()
  if ui[-1] not in endSent:
    ui += '.'
  ui = ui.split()
  for i in range(len(ui)):
    if (((ui[i][-1] in endSent) and (len(ui[i]) > 1) and (ui[i][-2] not in endSent)) and (ui[i] not in titles and ui[i] not in degrees)):
      ui[i] = ui[i][:-1] + "~~~"
  ui = ' '.join(ui)
  ui = ui.split("~~~")
  return ui

def cleanSents(ui):
  ui = cleanSentsGen(ui)
  for i in range(len(ui)):
    ui[i] = ui[i].translate(str.maketrans('','', string.punctuation))
  return ui

def avgWordsPerSent(sents):
  wps = []
  for sent in sents:
    words = sent.split()
    wps.append(len(words))
  return (sum(wps) / len(wps))

def getAvgWordsPerSent(ui):
  sents = cleanSents(ui)
  awps = avgWordsPerSent(sents)
  return awps

def avgPunctPerSent(sents):
  punctTots = {}
  for sent in sents:
    for character in sent:
      if ((character in string.punctuation) and (character in punctTots)):
        punctTots[character] += 1
      elif character in string.punctuation:
        punctTots[character] = 1
  aptps = dict.fromkeys(punctTots.keys(), 0)
  for k in aptps:
    aptps[k] = punctTots[k] / len(sents)
  return aptps

def getAvgPunctPerSent(ui):
  ui = cleanSentsGen(ui)
  aptps = avgPunctPerSent(ui)
  return aptps

def getDoc(fname):
  inputfile = open(fname, 'r')
  docText = inputfile.read()
  inputfile.close()
  string.punctuation += '—'
  doc = Document(docText)
  vocab = getWordFreqs(ui)
  awps = getAvgWordsPerSent(ui)
  aptps = getAvgPunctPerSent(ui)
  docinfo = {"vocab": vocab, "avgWordsPerSent": awps, "avgPunctPerSent": aptps}
  return docinfo
