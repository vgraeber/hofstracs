# Author: Vivian Graeber
# Date: 11/30/23
# Description: Player class file

class Player():
  def __init__(self, name, id, startingBankBalance=100):
    self.name = name
    self.id = id
    self.bankBalance = startingBankBalance
    self.betAmount = 0
    self.rollHist = []
    self.rollSum = 0
    self.debt = 0
    self.lost = False

  def __str__(self):
    lostStr = "has"
    if (self.lost):
      lostStr = "was not able to win. They"
    return f"{self.name} {lostStr} bet {self.betAmount} dollars. They currently have {self.bankBalance} dollars and owe {self.debt} dollars to the House. Their current roll total is {self.rollSum}."

  def initialBet(self, betAmount):
    self.betAmount += betAmount
    self.bankBalance -= betAmount

  def raiseBetBy(self, raiseAmount):
    self.betAmount += raiseAmount
    self.bankBalance -= raiseAmount

  def payDebt(self, amount):
    self.debt -= amount
    self.bankBalance -= amount

  def borrow(self, borrowAmount, interest):
    self.bankBalance += borrowAmount
    self.debt += (borrowAmount * interest)

  def addRoll(self, rollVals):
    self.rollHist.append(rollVals)
    self.rollSum += sum(rollVals)

  def passround(self):
    self.rollHist.append("pass")

  def addwinnings(self, amount):
    self.bankBalance += amount

  def clearHist(self):
    self.rollHist = []
    self.rollSum = 0
    self.betAmount = 0
