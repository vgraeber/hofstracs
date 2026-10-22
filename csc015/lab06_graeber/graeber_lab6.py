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

def numSentences(ui, abbvs=[]):
  endSent = ['.', '!', '?']
  titles = ["dr.", "esq.", "hon.", "jr.", "mr.", "mrs.", "ms.", "mx.", "messrs.", "mmes.", "msgr.", "rt.", "sr.", "st.", "ald.", "sen.", "gen.", "rep.", "gov.", "pres.", "col.", "lt.", "insp.", "asst.", "assoc.", "rev."]
  degrees = ["b.a.", "b.s.", "ph.d.", "m.d.", "b.f.a.", "b.s.b.a.", "b.s.ed.", "b.s.n.", "b.s.w.", "m.a.", "m.a.t.", "m.c.s.", "m.ed.", "m.f.a.", "m.h.s.a.", "m.l.s.", "m.mus.", "m.s.m.", "m.s.s.", "m.s.", "ed.d.", "d.p.c.", "ed.s.", "b.s.e.", "b.m.", "b.s.e.e.", "d.a.", "d.b.a.", "d.d.s.", "j.d.", "m.b.a.", "ed.m.", "d.m.l.", "d.min", "d.p.t.", "m.div.", "m.m.", "m.p.a.", "m.phil.", "m.s.a.", "m.s.e.e.", "m.s.l.i.s.", "m.s.p.t.", "m.th.", "r.n.", "s.t.m.", "th.d."]
  sentCount = 0
  w = ui.split()
  for i in w:
    if ((i[-1] in endSent) and (i[-2] not in endSent) and (i not in titles) and (i not in degrees) and (i not in abbvs)):
      sentCount += 1
  return sentCount

def numWordTokens(ui, addipunct=[]):
  punctuation = ['—', "..."]
  w = ui.split()
  for i in w:
    if ((i in punctuation) or (i in addipunct)):
      w.remove(i)
  return len(w)
