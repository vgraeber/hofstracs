# Author: Vivian Graeber
# Date: 11/17/23
# Description: Utility code for getting data given a text file
# Base code for vocab copied from lab 7
# Base code for avg words per sent copied from lab 3

import string
import nltk
from nltk.stem import WordNetLemmatizer
from nltk.corpus import wordnet
nltk.download("wordnet")
nltk.download("averaged_perceptron_tagger")
nltk.download("punkt")

def notEmpty(ui): # determines empty strings
  if (ui != ''):
    return True
  else:
    return False

def cleanInput(ui): # removes punctuation and splits the string at every word
  string.punctuation += '—'
  ui = ui.translate(str.maketrans('','', string.punctuation))
  ui = ui.split()
  ui = list(filter(notEmpty, ui))
  return ui

# from https://www.geeksforgeeks.org/python-lemmatization-approaches-with-examples/
# following 3 functions are for lemmatizing, or condensing, words
def pos_tagger(nltk_tag):
  tag_dict = {'J': wordnet.ADJ, 'N': wordnet.NOUN, 'V': wordnet.VERB, 'R': wordnet.ADV}
  return tag_dict.get(nltk_tag[0])

def wordnet_tagger(ui):
  wordnet_tagged = []
  for wrd in ui:
    pos_tagged = nltk.pos_tag(nltk.word_tokenize(wrd))
    for word, tag in pos_tagged:
      wordnet_tagged.append([word, pos_tagger(tag)])
  return wordnet_tagged

def lemmatizer(wordnet_tagged):
  lemmatized = []
  for word, tag in wordnet_tagged:
    if tag is None:
      lemmatized.append(word)
    else:
      lemmatized.append(WordNetLemmatizer().lemmatize(word, tag))
  return lemmatized

def wordFreqs(lemmatized): # creates a dictionary of the word vocabs that has their frequency as the values
  lemdict = {}
  for word in lemmatized:
    if word in lemdict:
      lemdict[word] += 1
    else:
      lemdict[word] = 1
  return lemdict

def getWordFreqs(ui): # returns the dictionary of the word frequency for the given text
  ui = cleanInput(ui)
  taggedinput = wordnet_tagger(ui)
  leminput = lemmatizer(taggedinput)
  lemWordFreq = wordFreqs(leminput)
  return lemWordFreq

def getSents(ui):
  endSent = ['.', '!', '?']
  titles = ["dr.", "esq.", "hon.", "jr.", "mr.", "mrs.", "ms.", "mx.", "messrs.", "mmes.", "msgr.", "rt.", "sr.", "st.", "ald.", "sen.", "gen.", "rep.", "gov.", "pres.", "col.", "lt.", "insp.", "asst.", "assoc.", "rev."]
  degrees = ["b.a.", "b.s.", "ph.d.", "m.d.", "b.f.a.", "b.s.b.a.", "b.s.ed.", "b.s.n.", "b.s.w.", "m.a.", "m.a.t.", "m.c.s.", "m.ed.", "m.f.a.", "m.h.s.a.", "m.l.s.", "m.mus.", "m.s.m.", "m.s.s.", "m.s.", "ed.d.", "d.p.c.", "ed.s.", "b.s.e.", "b.m.", "b.s.e.e.", "d.a.", "d.b.a.", "d.d.s.", "j.d.", "m.b.a.", "ed.m.", "d.m.l.", "d.min", "d.p.t.", "m.div.", "m.m.", "m.p.a.", "m.phil.", "m.s.a.", "m.s.e.e.", "m.s.l.i.s.", "m.s.p.t.", "m.th.", "r.n.", "s.t.m.", "th.d."]
  if ui[-1] not in endSent:
    ui += '.'
  ui = ui.split()
  for i in range(len(ui)):
    if (((ui[i][-1] in endSent) and (len(ui[i]) > 1) and (ui[i][-2] not in endSent)) and (ui[i].lower() not in titles and ui[i].lower() not in degrees)):
      ui[i] = ui[i][:-1] + "~~~"
  ui = ' '.join(ui)
  ui = ui.split("~~~")
  return ui

def cleanSents(ui):
  ui = getSents(ui)
  for i in range(len(ui)):
    ui[i] = ui[i].lower()
    ui[i] = ui[i].translate(str.maketrans('','', string.punctuation))
  return ui

def avgWordsPerSent(sents):
  wps = []
  for sent in sents:
    words = sent.split()
    wps.append(len(words))
  return (sum(wps) / len(wps))

def getWordsPerSent(ui):
  ui = cleanSents(ui)
  awps = avgWordsPerSent(ui)
  return awps

def avgPunctPerSent(sents):
  punctTots = {'.': 0}
  for sent in sents:
    punctTots['.'] += 1
    for character in sent:
      if ((character in string.punctuation) and (character in punctTots)):
        punctTots[character] += 1
      elif character in string.punctuation:
        punctTots[character] = 1
  aptps = dict.fromkeys(punctTots.keys(), 0)
  for k in aptps:
    aptps[k] = punctTots[k] / len(sents)
  return aptps

def getPunctPerSent(ui):
  ui = getSents(ui)
  aptps = avgPunctPerSent(ui)
  return aptps

def avgCharsPerWord(words):
  achpw = []
  for word in words:
    achpw.append(len(word))
  return (sum(achpw) / len(achpw))

def getCharsPerWord(ui):
  ui = cleanInput(ui)
  achpw = avgCharsPerWord(ui)
  return achpw

def avgCapsPerSent(sents):
  acps = []
  for sent in sents:
    cps = 0
    for character in sent:
      if (character.isupper()):
        cps += 1
    acps.append(cps)
  return (sum(acps) / len(acps))

def getCapsPerSent(ui):
  ui = getSents(ui)
  acps = avgCapsPerSent(ui)
  return acps
