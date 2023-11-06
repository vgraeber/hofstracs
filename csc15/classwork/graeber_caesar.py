key = 2

def check(ui):
  while not ui.isalpha():
    print("Invalid input.")
    ui = input("Please enter a message: ")
  return ui

def enc(mesaage):
  encMessage = ""
  lower = [ord('a'), ord('z')]
  upper = [ord('A'), ord('Z')]
  for letter in message:
    num = ord(letter)
    if ((lower[0] <= num) and (num <= lower[1])):
      num += key
      if (num > lower[1]):
        num = ((lower[0] - 1) + (num - lower[1]))
      encMessage += chr(num)
    else:
      num += key
      if (num > upper[1]):
        num = ((upper[0] - 1 )+ (num - upper[1]))
      encMessage += chr(num)
  return encMessage

def unenc(encMessage):
  unEncMessage = ""
  lower = [ord('a'), ord('z')]
  upper = [ord('A'), ord('Z')]
  for letter in encMessage:
    num = ord(letter)
    if ((lower[0] <= num) and (num <= lower[1])):
      num -= key
      if (num < lower[0]):
        num = ((lower[1] + 1) - (lower[0] - num))
      unEncMessage += chr(num)
    else:
      num -= key
      if (num < upper[0]):
        num = ((upper[1] + 1) - (upper[0] - num))
      unEncMessage += chr(num)
  return unEncMessage

def main():
  message = input("Please enter a message: ")
  message = check(message)
  encmes = enc(message)
  unencmes = unenc(encmes)
  print("The encrypted message is: " + encmes)
  print("The unencrypted message is: " + unencmes)

main()
