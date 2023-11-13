# Author: Vivian Graeber
# Date: 09/27/23
# Descripton: Multiplication table

def check(ui):
  while not ui.isnumeric():
    print("Invalid input. Please enter a number.")
    ui = input("Please enter a number: ")
  return int(ui)

def multiTable(ui):
  table = ""
  for i in range(ui):
    for j in range(ui):
      mult = str((i + 1) * (j + 1))
      table += mult.center(len(str(ui)) * 2)
    table += "\n"
  print(table)

def main():
  ui = input("Please enter a number: ")
  ui = check(ui)
  multiTable(ui)

main()
