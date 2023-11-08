# Author: Vivian Graeber
# Date: 11/08/23
# Description: Bag of words topic modeling practice

import nltk
from nltk.stem import WordNetLemmatizer
from nltk.corpus import wordnet
nltk.download("averaged_perceptron_tagger")
nltk.download("punkt")

lemtzr = WordNetLemmatizer()

# from https://www.geeksforgeeks.org/python-lemmatization-approaches-with-examples/
def pos_tagger(nltk_tag):
  tag_dict = {'J': wordnet.ADJ, 'N': wordnet.NOUN, 'V': wordnet.VERB, 'R': wordnet.ADV}
  return tag_dict.get(nltk_tag[0])

def wordnet_tagged(ui):
  pos_tagged = nltk.pos_tag(nltk.word_tokenize(ui))
  wordnet_tags = []
  for word, tag in pos_tagged:
    wordnet_tags.append([word, pos_tagger(tag)])
  return wordnet_tags

def leminput(wordnet_tagged):
  lemtzdinput = []
  for word, tag in wordnet_tagged:
    if tag is None:
      lemtzdinput.append(word)
    else:
      lemtzdinput.append(lemtzr.lemmatize(word, tag))
  return lemtzdinput

def lemdict(lemtzdinput):
  lemdict = {}
  for word in lemtzdinput:
    if word in lemdict:
      lemdict[word] += 1
    else:
      lemdict[word] = 1
  return lemdict

def main():
  sent = "the cat is sitting with the bats on the striped mat under many badly flying geese"
  sent_wordnet_tagged = wordnet_tagged(sent)
  sent_leminput = leminput(sent_wordnet_tagged)
  sent_lemdict = lemdict(sent_leminput)
  print(sent)
  print(sent_wordnet_tagged)
  print(sent_leminput)
  print(sent_lemdict)

main()
