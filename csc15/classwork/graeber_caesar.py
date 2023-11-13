def validmes(ui):
  for i in ui:
    if (not i.isalpha() and not i.isspace()):
      return False
  return True

def checkmes(ui):
  while ((len(ui) < 56) and not validmes(ui)):
    print("Invalid input. Your message must contain at least 56 characters and be made of only letters and spaces.")
    ui = input("Please enter a message: ")
  return ui

def isnum(ui):
  try:
    int(ui)
    return True
  except (TypeError, ValueError):
    return False

def checkkey(ui):
  while not isnum(ui):
    print("Invalid input. A key must be an integer.")
    ui = input("Please enter a key: ")
  return (int(ui) % 26)

def enc(message, key):
  encMessage = ""
  lower = [ord('a'), ord('z')]
  upper = [ord('A'), ord('Z')]
  for letter in message:
    num = ord(letter)
    if (letter.islower() and not letter.isspace()):
      num += key
      if (num > lower[1]):
        num = ((lower[0] - 1) + (num - lower[1]))
    elif not letter.isspace():
      num += key
      if (num > upper[1]):
        num = ((upper[0] - 1 )+ (num - upper[1]))
    encMessage += chr(num)
  return encMessage

def unenc(encMessage):
  unEncMessages = []
  lower = [ord('a'), ord('z')]
  upper = [ord('A'), ord('Z')]
  for key in range(26):
    unEncMessage = ""
    for letter in encMessage:
      num = ord(letter)
      if (letter.islower() and not letter.isspace()):
        num -= key
        if (num < lower[0]):
          num = ((lower[1] + 1) - (lower[0] - num))
      elif not letter.isspace():
        num -= key
        if (num < upper[0]):
          num = ((upper[1] + 1) - (upper[0] - num))
      unEncMessage += chr(num)
    unEncMessages.append(unEncMessage)
  return unEncMessages

def main():
  message = input("Please enter a message: ")
  message = checkmes(message)
  key = input("Please enter a key: ")
  key = checkkey(key)
  encmes = enc(message, key)
  unencmes = unenc(encmes)
  print("The encrypted message is: " + encmes)
  print("The unencrypted message is one of the following: ")
  for i in range(26):
    print(str(i + 1).center(4), unencmes[i])

main()
