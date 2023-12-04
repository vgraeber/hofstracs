# Author: Vivian Graeber
# Date: 11/30/23
# Description: Game class file

from player import Player
from dice import Dice

# default dice vars: numDice=1, numDiceSides=6, rollsPerRound=1, diffNumDicePerRound=False, rprFormula=""
# default player vars: startingBankBalance=100; manual player vars: name, id

class Game():
  def __init__(self, numPlayers=4, totRounds=12, diceLimit=12, loanInterestPercentage=50, minimumBet=5, numDice=1, numDiceSides=6, rollsPerRound=1, diffNumDicePerRound=False, rprFormula="", startingBankBalance=100):
    self.numPlayers = numPlayers
    self.totRounds = totRounds
    self.currentRound = 0
    self.diceLimit = diceLimit
    self.interest = loanInterestPercentage
    self.minimumBet = minimumBet
    self.gameDice = Dice(numDice, numDiceSides, rollsPerRound, diffNumDicePerRound, rprFormula)
    self.playerlist = self.getPlayers(startingBankBalance)
    self.pot = self.getBettingPool()

  def __str__(self):
    return f"This game is in its {self.currentRound} round, with {len(self.playerList)} players left in the game, and a betting pool of {self.pot}."

  def getPlayers(self, startingBankBalance):
    playerlist = []
    for i in range(self.numPlayers):
      name = input(f"Player {i + 1}, please input your name: ")
      playerlist.append(Player(name, i, startingBankBalance))
    return playerlist

  def isInt(num):
    try:
      int(num)
      return True
    except (ValueError, TypeError):
      return False

  def checkInitBet(self, betAmount):
    while (not isInt(betAmount) and (betAmount < self.minimumBet)):
      print(f"That is not a valid bet. Bets must be integers and greater than {self.minimumBet}")
      betAmount = input("Please input your starting bet: ")
    return betAmount

  def getBettingPool(self):
    pool = 0
    for person in self.playerlist:
      betAmount = input(f"{person.name}, please input your starting bet: ")
      betAmount = self.checkInitBet(betAmount)
      person.initBet(betAmount)
      pool += betAmount
    return pool

  def cleaninput(doroll):
    doroll = doroll.lower()
    acceptable = ['y', 'n']
    while doroll not in acceptable:
      print("That is not a valid answer. Please enter either 'Y' or 'N'.")
      doroll = input("Do you want to roll? ")
    return doroll

  def removePlayer(person):
    ind = 0
    for i in range(len(self.playerlist)):
      if (self.playerlist[i].id == person.id):
        ind = i
    self.playerlist.pop(ind)

  def checklocked(self):
    for person in self.playerlist:
      if (person.rollSum != self.diceLimit):
        return False
    return True

  def gethighestplayers(self):
    highest = []
    highestroll = 0
    for person in self.playerlist:
      if (person.rollSum <= self.diceLimit):
        if (person.rollSum > highestroll):
          highestroll = person.rollSum
          highest = [person]
        elif (person.rollsum == highestroll):
          highest.append(person)
    return highest

  def getWinner(self):
    highestplayers = self.gethighestplayers()
    if ((highestplayers == 1) or (len(self.playerlist) == 1)):
      return highestplayers[0]
    else:
      return "House"

  def endGame(self):
    if ((self.currentRound == self.totRounds) or (len(self.playerlist) == 0) or (len(self.playerlist) == 1) or self.checklocked()):
      return True
    return False

  def runRound(self):
    self.currentRound += 1
    for person in self.playerlist:
      doroll = input(f"{person.name}, do you want to roll? (Y/N) ")
      doroll = cleaninput(doroll)
      if (doroll == 'y'):
        diceRoll = self.gameDice.roll(self.currentRound)
        person.addRoll(diceRoll)
      else:
        person.passround()
    for person in self.playerlist:
      if (person.rollSum > self.diceLimit):
        person.lost = True
        self.removePlayer(player)
    if self.endGame():
      winner = self.getwinner()
      if (winner != "House"):
        winner.addwinnings(self.pot)
        print(f"{winner.name} has won.")
      else:
        print("The House has won.")
      self.pot = 0
