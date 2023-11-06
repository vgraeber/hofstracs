# Author: Vivian Graeber
# Date: 11/02/23
# Description: Lab 6 abstraction code

import nltk
from textblob import TextBlob as tb
nltk.download("averaged_perceptron_tagger")
nltk.download("wordnet")

def condenselist(t):
  nl = []
  for i in t:
    if i not in nl:
      nl.append(i)
  return nl

# from https://www.geeksforgeeks.org/python-lemmatization-approaches-with-examples/
def numWordVocabs(sentence):
  sent = tb(sentence)
  tag_dict = {'J': 'a', 'N': 'n', 'V': 'v', 'R': 'r'}
  word_tags = [(w, tag_dict.get(pos[0], 'n')) for w, pos in sent.tags]
  lem_list = [wd.lemmatize(tag) for wd, tag in word_tags]
  return len(condenselist(lem_list))

def numSentences(ui):
  endSent = ['.', '!', '?']
  titles = ["dr.", "esq.", "hon.", "jr.", "mr.", "mrs.", "ms.", "mx.", "messrs.", "mmes.", "msgr.", "rt.", "sr.", "st.", "ald.", "sen.", "gen.", "rep.", "gov.", "pres.", "col.", "lt.", "insp.", "asst.", "assoc."]
  degrees = ["B.A.", "B.S."]
  sentCount = 0
  w = ui.split()
  for i in w:
    if ((i[-1] in endSent) and (i[-2] not in endSent) and (i not in titles) and (i not in degrees)):
      sentCount += 1
  return sentCount

def numWordTokens(ui):
  punctuation = ['—', "..."]
  w = ui.split()
  for i in w:
    if i in punctuation:
      w.remove(i)
  return len(w)
