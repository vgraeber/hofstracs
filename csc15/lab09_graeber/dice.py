# Author: Vivian Graeber
# Date: 11/30/23
# Description: Dice class file

import random

class Dice():
  def __init__(self, numDice=1, numDiceSides=6, rollsPerRound=1, diffNumDicePerRound=False, rprFormula=""):
    self.rollVals = [0] * numDice
    self.numDice = numDice
    self.numSides = numDiceSides
    self.rpr = rollsPerRound
    self.dndpr = diffNumDicePerRound
    self.rprf = rprFormula
    if (diffNumDicePerRound and (rprFormula == "")):
      self.rprf = "1r"

  def __str__(self):
    rollDiceWord = "dice"
    wordEach = "each "
    diceWord = "dice"
    wordValues = "values"
    wordAre = "are"
    if (self.rpr == 1):
      rollDiceWord = "die"
      wordEach = ""
    if (self.numDice == 1):
      diceWord = "die"
      wordValues = "value"
      wordAre = "is"
    return f"You are rolling {self.rpr} {rollDiceWord} per round, {wordEach}with {self.numSides}. Currently, you are rolling {self.numDice} {diceWord}."

  def calcRPR(self, round):
    modRPR = self.rprf.replace('r', str(round))
    return eval(modRPR)

  def roll(self, round):
    if self.dndpr:
      self.rpr = self.calcRPR(round)
    for i in range(self.rpr):
      for j in range(self.numDice):
        self.rollVals[j] = random.randint(1, self.numSides)
    return self.rollVals
