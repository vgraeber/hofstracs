# Author: Vivian Graeber
# Date: 11/30/23
# Description: Game class file

from player import Player
from dice import Dice

# default dice vars: numDice=1, numDiceSides=6, rollsPerRound=1, diffNumDicePerRound=False, rprFormula=""
# default player vars: startingBankBalance=100, minimumBet=5

class Game():
  def __init__(self, numPlayers=4, totRounds=12, diceLimit=12, loanInterestPercentage=50, minimumBet=5, numDice=1, numDiceSides=6, rollsPerRound=1, diffNumDicePerRound=False, rprFormula="", startingBankBalance=100):
    self.numPlayers = numPlayers
    self.totRounds = totrounds
    self.diceLimit = diceLimit
    self.pot = 0
    self.interest = loanInterestPercentage
    self.gameDice = Dice(numDice, numDiceSides, rollsPerRound, diffNumDicePerRound, rprFormula)
    self.playerList = self.getPlayers(startingBankBalance, minimumBet)

  def __str__(self):
    return f""

  def getPlayers(self, startingBankBalance, minimumBet):
    playerlist = []
    for i in range(self.numPlayers):
      name = input("Please input your name: ")
      playerlist.append(Player(name, startingBankBalance, minimumBet))
    return playerlist

  
