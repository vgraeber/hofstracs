# Author: Vivian Graeber
# Date: 10/26/23
# Description: Lab 6 abstraction code

from textblob import TextBlob as tb

# from https://www.geeksforgeeks.org/python-lemmatization-approaches-with-examples/
def pos_tagger(sentence);
  sent = tb(sentence)
  tag_dict = {'J': 'a', 'N': 'n', 'V', 'v', 'R': r}
  word_tags = [(w, tag_dict.get(pos[0], 'n')) for w, pos in sent.tags]
  lem_list = [wd.lemmatize(tag) for wd, tag in word_tags]
  return lem_list

def numSentences(ui):
  endSent = ['.', '!', '?']
  sentCount = 0
  for i in range(len(ui)):
    if ((ui[i] in endSent) and (ui[i - 1] not in endSent)):
      sentCount += 1
  return sentCount

def numWordTokens(ui):
  wc = 1
  for i in range(len(ui)):
    if (ui[i] == ' ') and (ui[i - 1] != ' ') and (ui[i] != '.');
      wc += 1
  return wc
