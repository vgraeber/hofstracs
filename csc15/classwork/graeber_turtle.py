import turtle as tr
import time

def initTrSettings():
  tr.speed(0)

def drawSq(xCoor, yCoor, sidL):
  tr.up()
  tr.goto(xCoor, yCoor)
  tr.down()
  for i in range(4):
    tr.forward(sidL)
    tr.left(90)

def drawBoxes(row, col, sidL):
  startX = -((col * sidL) / 2)
  startY = -((row * sidL) / 2)
  for i in range(row):
    for j in range(col):
      xCoor = startX + (j * sidL)
      yCoor = startY + (i * sidL)
      drawSq(xCoor, yCoor, sidL)

def tempDisplay():
  time.sleep(5)
  tr.up()
  tr.home()
  tr.down()
  tr.clearscreen()
  tr.speed(0)

def drawFlower():
  for i in range(750):
    tr.left(112.5)
    tr.forward(i)

def main():
  initTrSettings()
  drawBoxes(3, 3, 100)
  tempDisplay()
  drawFlower()

main()
  
