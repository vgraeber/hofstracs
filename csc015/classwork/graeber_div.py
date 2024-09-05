def isfloat(num):
  try:
    float(num)
    return True
  except (TypeError, ValueError):
    return False

def checknums(num, type):
  while not isfloat(num):
    print("Not a number. Please enter a number.")
    num = input("Please enter your " + type + ": ")
  return float(num)

def getinputs():
  ui1 = input("Please enter your numerator: ")
  ui1 = checknums(ui1, "numerator")
  ui2 = input("Please enter your denominator: ")
  ui2 = checknums(ui2, "denominator")
  return ui1, ui2

def candiv(n1, n2):
  try:
    n1 / n2
    return True
  except ZeroDivisionError:
    print("Cannot divide by 0. Pleae re-renter your numbers.")
    return False

def div(n1, n2):
  while not candiv(n1, n2):
    n1, n2 = getinputs()
  return (n1 / n2)

def main():
  n1, n2 = getinputs()
  d = div(n1, n2)
  print(str(n1) + '/' + str(n2) + " = " + str(d))

main()
