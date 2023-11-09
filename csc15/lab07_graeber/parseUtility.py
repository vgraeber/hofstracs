# Author: Vivian Graeber
# Date: 11/08/23
# Description: Bag of words topic modeling practice

import string
from collections import Counter
import nltk
from nltk.stem import WordNetLemmatizer
from nltk.corpus import wordnet
nltk.download("averaged_perceptron_tagger")
nltk.download("punkt")

def areWords(ui):
  if (ui != ''):
    return True
  else:
    return False

def cleanInput(ui):
  string.punctuation += '—'
  ui = ui.translate(str.maketrans('','', string.punctuation))
  ui = ui.split()
  ui = list(filter(areWords, ui))
  return ui

# from https://www.geeksforgeeks.org/python-lemmatization-approaches-with-examples/
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

def wordFreqs(lemmatized):
  lemdict = {}
  for word in lemmatized:
    if word in lemdict:
      lemdict[word] += 1
    else:
      lemdict[word] = 1
  return Counter(lemdict)

def getWordFreqs(fname):
  inputfile = open(fname, 'r')
  ui = inputfile.read().lower()
  inputfile.close()
  ui = cleanInput(ui)
  taggedinput = wordnet_tagger(ui)
  leminput = lemmatizer(taggedinput)
  lemWordFreq = wordFreqs(leminput)
  return lemWordFreq
