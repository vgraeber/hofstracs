# Author: Vivian Graeber
# Date: 10/19/23
# Revision history
# Name       Date        Description
# ------------------------------------
# Vivian     10/19/23    Initial code

import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import yfinance as yf

def calcAvg(dictionary, column):
  colVals = dictionary[column].values
  colSum = np.sum(colVals)
  return str(colSum / len(colVals))

def stockInfo():
  stockList = open("graeber_stock_list.txt", 'r')
  slist = stockList.read().split(',')
  slist.pop()
  stockList.close()
  nl = "\n"
  for stockSymb in slist:
    company = yf.Ticker(stockSymb)
    desc = company.info.get("longBusinessSummary")
    priceHist = company.history(period="3mo")
    aOpen = calcAvg(priceHist, "Open")
    aHigh = calcAvg(priceHist, "High")
    aLow = calcAvg(priceHist, "Low")
    aClose = calcAvg(priceHist, "Close")
    ssOut = open(stockSymb + "_graeber.out", 'w')
    ssOut.write(desc + nl + "Average Open: " + aOpen + nl + "Average High: " + aHigh + nl + "Average Low: " + aLow + nl + "Average Close: " + aClose)
    ssOut.close()
    plt.figure()
    sns.lineplot(data=priceHist, x="Date", y="Close")
    plt.xticks(rotation=35)
    plt.tight_layout()
    plt.savefig(stockSymb + "_graeber.png")

def main():
  stockInfo()

main()
