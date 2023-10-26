# Author(s): VIvian Graeber, Christian Ridley
# Date: 10/25/23
# Description: Top-down design exercise

def isFLoat(ui):
  try:
    float(ui)
    return True
  except ValueError:
    return False

def checkinput(ui, t): #Second variable was no0t in original design plan, design plan has been edited
  if (t == "dorw"):
    while ui not in ['d', 'w', "distance", "weight"]:
      print("Error. Not a valid input.")
      ui = input("Distance or Weight? ").lower()
    ui = ui[0]
  elif (t == "mori"):
    while ui not in ['m', 'i', "metric", "imperial"]:
      print("Error. Not a valid measurement system.")
      ui = input("Metric or Imperial? ").lower()
    ui = ui[0]
  elif isinstance(t, list):
    if (t[1] == 'd'):
      if (t[2] == 'm'):
        while ui not in ["cm", 'm', "centimeters", "meters"]:
          print("Error. Invalid type.")
          ui = input("Please enter the type of your input: ").lower()
        ui = ui[0]
        if (ui == 'c'):
          ui = "cm"
      else:
        while ui not in ["in", "ft", "yds", "inches", "feet", "yards"]:
          print("Error. Invalid type.")
          ui = input("Please enter the type of your input: ").lower()
        ui = ui[0:2]
        if (ui == 'fe'):
          ui = "ft"
        elif (ui == 'ya'):
          ui = "yds"
    elif (t == "uin"):
      if (t[2] == 'm'):
        while ui not in ['g', "kg", "grams", "kilograms"]:
          print("Error. Invalid type.")
          ui = input("Please enter the type of your input: ").lower()
        ui = ui[0]
        if (ui == 'k'):
          ui = "kg"
      else:
        while ui not in ["lbs", "pounds"]:
          print("Error. Invalid type.")
          ui = input("Please enter the type of your input: ").lower()
        ui = "lbs"
  else:
    while not isFloat(ui):
      print("Error. Not a number.")
      ui = input("Please enter the number you wish to convert: ")
    ui = float(ui)
  return ui

def dorw():
  ui = input("Distance or Weight? ").lower()
  ui = checkinput(ui, "dorw")
  return ui

def mori():
  ui = input("metric or Imperial? ").lower()
  ui = checkinput(morw, "morw")
  return ui

def it(dorw, mori):
  ui = input("Please enter the type of your input: ").lower()
  ui = checkinput(ui, [dorw, mori])
  return ui

def uin():
  ui = input("Please enter the number you wish to convert: ")
  ui = checkinput(ui, "uin")

def conv(dorw, mori, it, uin):
  convs = []
  if (dorw == 'd'):
    if (mori == 'm'):
      i = ""
      ft = ""
      yds = ""
      if (it == 'm'):
        i += str(uid * 39.3701)
        ft += str(uid * 3.28084)
        yds += str(uid * 1.09361)
      elif (it == "cm"):
        i += str(uid * .393701)
        ft += str(uid * .0328084)
        yds += str(uid * .0109361)
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
        cm += str(uin * 2.54)
        m += str(uin * .0254)
      elif (it == "ft"):
        cm += str(uin * 30.48)
        m += str(uin * .3048)
      elif (it = "yds"):
        cm += str(uin * 91.44)
        m += str(uin * .9144)
      cm += 'cm'
      m += "m"
      convs.append(cm)
      convs.append(m)
  elif (dorw == 'w'):
    if (mori == 'm'):
      lbs = ""
      if (it = 'g'):
        lbs += str(uin / 453.592)
      elif (it == "kg"):
        lbs += str(uin / .453592)
      lbs += "lbs"
      convs.append(lbs)
    elif (mori == 'i'):
      g = ""
      kg = ""
      if (it == "lbs"):
        g += str(uin * 453.592)
        kg += str(uin * .453592)
      g += 'g'
      kg += "kg"
      convs.append(g)
      convs.append(kg)

def main():
  dorw = dorw()
  mori = mori()
  it = it(dorw, mori)
  uin = uin()
  conv = conv(dorw, mori, it, uin)
