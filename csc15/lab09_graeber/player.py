# Author: Vivian Graeber
# Date: 11/30/23
# Description: Player class file

class Player():
  def __init__(self, name, startingBankBalance=100, minimumBet=5):
    self.name = name
    self.bankBalance = startingBankBalance
    self.betAmount = 0
    self.minimumBet = minimumBet
    self.rollHist = []
    self.rollSum = 0
    self.debt = 0
    self.lost = False

  def __str__(self):
    lostStr = "has"
    if (self.lost):
      lostStr = "was not able to win. They"
    return f"{self.name} {lostStr} bet {self.betAmount} dollars. They currently have {self.bankBalance} dollars and owe {self.debt} dollars to the House. Their current roll total is {self.rollSum}."

  def initialBet(betAmount>=self.minimumBet):
    if (betAmount < self.minimumBet):
      betAmount = self.minimumBet
    self.betAmount += betAmount
    self.bankBalance -= betAmount

  def raiseBetBy(raiseAmount):
    self.betAmount += raiseAmount
    self.bankBalance -= raiseAmount

  def raiseBetTo(betAmount>=self.minimumBet):
    self.betAmount = betAmount
    self.bankBalance -= betAmount

  def payDebt(amount):
    self.debt -= amount
    self.bankBalance -= amount

  def borrow(borrowAmount, interest):
    self.bankBalance += borrowAmount
    self.debt += (borrowAmount * interest)

  def addRoll(rollVals):
    self.rollHist.append(rollVals)
    self.rollSum += sum(rollVals)
