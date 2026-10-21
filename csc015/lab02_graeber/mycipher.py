# Author: Vivian Graeber
# Date: 9/14/23
# Description: lab2 work question 2

def checkBounds(num, key):
  lower = [ord('a'), ord('z')]
  upper = [ord('A'), ord('Z')]
  ori = (num - key)
  if ((lower[0] <= ori) and (ori <= lower[1])):
    if (num > lower[1]):
      num = ((num - lower[1]) + (lower[0] - 1))
    if (num < lower[0]):
      num = ((lower[1] + 1) - (lower[0] - num))
  if ((upper[0] <= ori) and (ori <= upper[1])):
    if (num > upper[1]):
      num = ((num - upper[1]) + (upper[0] - 1))
    if (num < upper[0]):
      num = ((upper[1] + 1) - (upper[0] - num))
  return num

def encryptCaesar(key, plainText):
  encryptedText = ""
  for i in plainText:
    letter = (ord(i) + key)
    encryptedText += chr(checkBounds(letter, key))
  return encryptedText

def decryptCaesar(key, encryptedText):
  decryptedText = ""
  for i in encryptedText:
    letter = (ord(i) - key)
    decryptedText += chr(checkBounds(letter, -key))
  return decryptedText

def main():
  plainText = input("Please enter the message you want to encrypt: ")
  key = int(input("Please enter the key you want to shift your message by: "))
  encryptedText = encryptCaesar(key, plainText)
  decryptedText = decryptCaesar(key, encryptedText)
  if (plainText != decryptedText):
    print("There was an error with the cipher. Please recheck your code.")
  print("Original message:", plainText)
  print("Encrypted message:", encryptedText)
  print("Decrypted message:", decryptedText)

main()
