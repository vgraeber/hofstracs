# Author(s): VIvian Graeber, Christian Ridley
# Date: 10/25/23
# Description: Top-down design exercise
#--------------------------------------
# Date          Description
# 10/30/23      Edited code for better clarity and to remove errore

import decimal as dec

def checkdw(ui):
  while ui not in ['d', 'w', "distance", "weight"]:
    print("Error. Not a valid input.")
    ui = input("Distance or Weight? ").lower()
  return ui[0]

def dorw():
  ui = input("Distance or Weight? ").lower()
  ui = checkdw(ui)
  return ui

def checkmi(ui):
  while ui not in ['m', 'i', "metric", "imperial"]:
    print("Error. Not a valid measurement system.")
    ui = input("Metric or Imperial? ").lower()
  return ui[0]

def mori():
  ui = input("Metric or Imperial? ").lower()
  ui = checkmi(ui)
  return ui

def checkit(ui, dorw, mori):
  if (dorw == 'd'):
    if (mori == 'm'):
      while ui not in ["cm", 'm', "centimeters", "meters"]:
        print("Error. Invalid type. Valid types are: 'centimeters', 'meters'")
        ui = input("Please enter the type of your input: ").lower()
      ui = ui[0]
      if (ui == 'c'):
        ui = "cm"
    elif (mori == 'i'):
      while ui not in ["in", "ft", "yds", "inches", "feet", "yards"]:
        print("Error. Invalid type. Valid types are: 'inches', 'feet', 'yards'")
        ui = input("Please enter the type of your input: ").lower()
      ui = ui[0]
      if (ui == 'i'):
        ui = "in"
      elif (ui == 'f'):
        ui = "ft"
      elif (ui == 'y'):
        ui = "yds"
  elif (dorw == 'w'):
    if (mori == 'm'):
      while ui not in ['g', "kg", "grams", "kilograms"]:
        print("Error. Invalid type. Valid types are: 'grams', 'kilograms'")
        ui = input("Please enter the type of your input: ").lower()
      ui = ui[0]
      if (ui == 'k'):
        ui = "kg"
    elif (mori == 'i'):
      while ui not in ["lbs", "pounds"]:
        print("Error. Invalid type. Valid types are: 'pounds'")
        ui = input("Please enter the type of your input: ").lower()
      ui = "lbs"
  return ui

def it(dorw, mori):
  ui = input("Please enter the type of your input: ").lower()
  ui = checkit(ui, dorw, mori)
  return ui

def isdec(ui):
  try:
    dec.Decimal(ui)
    return True
  except (TypeError, ValueError):
    return False

def checkuin(ui):
  while not isdec(ui):
    print("Error. Not a number.")
    ui = input("Please enter the number you wish to convert: ")
  return dec.Decimal(ui)

def uin():
  ui = input("Please enter the number you wish to convert: ")
  ui = checkuin(ui)
  return ui

def conv(dorw, mori, it, uin):
  convs = []
  if (dorw == 'd'):
    if (mori == 'm'):
      i = ""
      ft = ""
      yds = ""
      if (it == 'm'):
        i += str(uin * dec.Decimal("39.3700787402"))
        ft += str(uin * dec.Decimal("3.28083989501"))
        yds += str(uin * dec.Decimal("1.0936132983"))
      elif (it == "cm"):
        i += str(uin * dec.Decimal(".393700787402"))
        ft += str(uin * dec.Decimal(".0328083989501"))
        yds += str(uin * dec.Decimal(".010936132983"))
      i += "in"
      ft += "ft"
      yds += "yds"
      convs.append(i)
      convs.append(ft)
      convs.append(yds)
    elif (mori == 'i'):
      cm = ""
      m = ""
      if (it == "in"):
        cm += str(uin * dec.Decimal("2.54"))
        m += str(uin * dec.Decimal(".0254"))
      elif (it == "ft"):
        cm += str(uin * dec.Decimal("30.48"))
        m += str(uin * dec.Decimal(".3048"))
      elif (it == "yds"):
        cm += str(uin * dec.Decimal("91.44"))
        m += str(uin * dec.Decimal(".9144"))
      cm += 'cm'
      m += "m"
      convs.append(cm)
      convs.append(m)
  elif (dorw == 'w'):
    if (mori == 'm'):
      lbs = ""
      if (it == 'g'):
        lbs += str(uin * dec.Decimal(".00220462262185"))
      elif (it == "kg"):
        lbs += str(uin * dec.Decimal("2.20462262185"))
      lbs += "lbs"
      convs.append(lbs)
    elif (mori == 'i'):
      g = ""
      kg = ""
      if (it == "lbs"):
        g += str(uin * dec.Decimal("453.5923699997"))
        kg += str(uin * dec.Decimal(".4535923699997"))
      g += 'g'
      kg += "kg"
      convs.append(g)
      convs.append(kg)
  return convs

def main():
  dw = dorw()
  mi = mori()
  label = it(dw, mi)
  num = uin()
  convs = conv(dw, mi, label, num)
  for i in convs:
    print(str(num) + label + " = " + i)

main()
