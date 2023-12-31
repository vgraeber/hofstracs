# Author: Vivian Graeber
# Date: 11/20/23
# Description: Utility code for getting data given a text file
# Base code for avg words per sent copied from lab 3

import string
import nltk
from nltk.stem import WordNetLemmatizer
from nltk.corpus import wordnet
nltk.download("wordnet")
nltk.download("averaged_perceptron_tagger")
nltk.download("punkt")

string.punctuation += '—'

def notEmpty(ui): # determines empty strings
  if (ui != ''):
    return True
  else:
    return False

def remPunct(ui):
  ui = ui.translate(str.maketrans('','', string.punctuation))
  return ui

def splitInput(ui):
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

# ADJ 	adjective 	new, good, high, special, big, local
# ADP 	adposition 	on, of, at, with, by, into, under
# ADV 	adverb 	really, already, still, early, now
# CONJ 	conjunction 	and, or, but, if, while, although
# DET 	determiner, article 	the, a, some, most, every, no, which
# NOUN 	noun 	year, home, costs, time, Africa
# NUM 	numeral 	twenty-four, fourth, 1991, 14:24
# PRT 	particle 	at, on, out, over per, that, up, with
# PRON 	pronoun 	he, their, her, its, my, I, us
# VERB 	verb 	is, say, told, given, playing, would
# . 	punctuation marks 	. , ; !
# X 	other 	ersatz, esprit, dunno, gr8, univeristy

def nltk_tagger(ui):
  nltk_tagged = []
  for word in ui:
    pos_tagged = nltk.pos_tag(nltk.word_tokenize(word))
    nltk_tagged.append(pos_tagged)
  return nltk_tagged

def tagsOnly(ui):
  tags = []
  for pair in ui:
    for i in pair:
      tags.append(i[1])
  return tags

def freqsDict(ui): # creates a dictionary of the inputs that has their frequencies as the values
  freqdict = {}
  for i in ui:
    if i in freqdict:
      freqdict[i] += 1
    else:
      freqdict[i] = 1
  return freqdict

def getVocabFreqs(ui): # returns the dictionary of the vocab frequency for the given text
  lowerui = ui.lower()
  nopunctui = remPunct(lowerui)
  splitui = splitInput(nopunctui)
  taggedui = wordnet_tagger(splitui)
  lemui = lemmatizer(taggedui)
  vocabfreqs = freqsDict(lemui)
  return vocabfreqs

def getTypeFreqs(ui):
  splitui = splitInput(ui)
  taggedui = nltk_tagger(splitui)
  tags = tagsOnly(taggedui)
  tagfreqs = freqsDict(tags)
  return tagfreqs

def notNotEndSent(word):
  perlocs = []
  if ((len(word) > 3) and (word[-3:] == "...")):
    word = word[:-3]
  for i in range(len(word)):
    if (word[i] == '.'):
      perlocs.append(i)
  if (len(perlocs) > 1):
      return False
  return True

def getSents(ui):
  endSent = ['.', '!', '?']
  titles = ["dr.", "esq.", "hon.", "jr.", "mr.", "mrs.", "ms.", "mx.", "messrs.", "mmes.", "msgr.", "rt.", "sr.", "st.", "ald.", "sen.", "gen.", "rep.", "gov.", "pres.", "col.", "lt.", "insp.", "asst.", "assoc.", "rev."]
  if ui[-1] not in endSent:
    ui += '.'
  ui = ui.split()
  for i in range(len(ui)):
    if ((ui[i][-1] in endSent) and (len(ui[i]) > 1) and notNotEndSent(ui[i]) and (ui[i].lower() not in titles)):
      ui[i] = ui[i][:-1] + "~~~"
    elif ((len(ui[i]) > 1) and (ui[i][-1] in string.punctuation) and (ui[i][-2] in endSent)):
      ui[i] += "~~~"
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
  ui = remPunct(ui)
  ui = splitInput(ui)
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
