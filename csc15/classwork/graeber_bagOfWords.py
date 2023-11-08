# Author: Vivian Graeber
# Date: 11/08/23
# Description: Bag of words topic modeling practice

import string
import nltk
from nltk.stem import WordNetLemmatizer
from nltk.corpus import wordnet
nltk.download("averaged_perceptron_tagger")
nltk.download("punkt")

def isWords(ui):
  if ((ui != '') and (ui not in string.punctuation)):
    return True
  else:
   return False

def cleanInput(ui):
  ui = ui.splitlines()
  ui = list(filter(isWords, ui))
  nui = []
  string.punctuation += '—'
  for paragraph in ui:
    paragraph = paragraph.split()
    words = list(filter(isWords, paragraph))
    for i in words:
      i = i.strip(string.punctuation)
      nui.append(i)
  return nui

# from https://www.geeksforgeeks.org/python-lemmatization-approaches-with-examples/
def pos_tagger(nltk_tag):
  tag_dict = {'J': wordnet.ADJ, 'N': wordnet.NOUN, 'V': wordnet.VERB, 'R': wordnet.ADV}
  return tag_dict.get(nltk_tag[0])

def wordnet_tagger(ui):
  wordnet_tagged = []
  for line in ui:
    pos_tagged = nltk.pos_tag(nltk.word_tokenize(line))
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

def lemWordFreq(lemmatized):
  lemdict = {}
  for word in lemmatized:
    if word in lemdict:
      lemdict[word] += 1
    else:
      lemdict[word] = 1
  return lemdict

def main():
  inputfile = open("../lab06_graeber/obama-2013.txt", 'r')
  ui = inputfile.read().lower()
  inputfile.close()
  ui = cleanInput(ui)
  print(ui)
  sent_wordnet_tagged = wordnet_tagger(ui)
  sent_leminput = lemmatizer(sent_wordnet_tagged)
  sent_lemdict = lemWordFreq(sent_leminput)
  print(sent_lemdict)

main()
