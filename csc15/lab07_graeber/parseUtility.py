# Author: Vivian Graeber
# Date: 11/09/23
# Description: Bag of words topic modeling practice

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

def getWordFreqs(fname): # returns the dictionary of the word frequency for the given file
  inputfile = open(fname, 'r')
  ui = inputfile.read().lower()
  inputfile.close()
  ui = cleanInput(ui)
  taggedinput = wordnet_tagger(ui)
  leminput = lemmatizer(taggedinput)
  lemWordFreq = wordFreqs(leminput)
  return lemWordFreq
