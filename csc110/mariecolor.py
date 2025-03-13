# Author: Vivian Graeber
# Date: 03/12/25
# Description: Python program for converting RGB values into MARIE color values

def check(ui, color):
  while ((not ui.isnumeric()) or ((int(ui) < 0) or (255 < int(ui)))):
    print("Invalid input. Values should be an integer between 0 and 255 (inclusive).")
    ui = input("Please enter your " + color + " value: ")
  return int(ui)

def decMarieColor(n):
  decMarieColor = round((31/255) * n)
  return decMarieColor

def decToBin5(n):
  binN = []
  while (n != 0):
    binN.append(n % 2)
    n //= 2
  binN.reverse()
  while (len(binN) < 5):
    binN.insert(0, 0)
  return binN

def bin4ToHex(n):
  decN = 0
  exp = 0
  for i in range(len(n) - 1, -1, -1):
    if (n[i] == 1):
      decN += (2 ** exp)
    exp += 1
  hex = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"]
  hexN = hex[decN]
  return hexN

def bin16ToHex(binRed, binGreen, binBlue):
  binMarieColor = [0] + binRed + binGreen + binBlue
  hexMarieColor = ""
  for i in range(4):
    binN = binMarieColor[(4 * i):(4 * (i + 1))]
    hexMarieColor += bin4ToHex(binN)
  return hexMarieColor

def main():
  red = input("Please enter your red value: ")
  red = check(red, "red")
  green = input("Please enter your green value: ")
  green = check(green, "green")
  blue = input("Please enter your blue value: ")
  blue = check(blue, "blue")
  binRed = decToBin5(decMarieColor(red))
  binGreen = decToBin5(decMarieColor(green))
  binBlue = decToBin5(decMarieColor(blue))
  hexMarieColor = bin16ToHex(binRed, binGreen, binBlue)
  print("The MARIE hexadecimal color code for RGB(" + str(red) + ", " + str(green) + ", " + str(blue) + ") is: " + hexMarieColor)

main()
