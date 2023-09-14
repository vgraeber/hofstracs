# Chapter 2 - Python crash course
"""
fname = input("Please enter your first name: ")
lname = input("Please enter your last name: ")
hours = int(input("Please enter your hourse worked: "))
rate = 62.55
print("Hello", fname, lname)
print("Your gross salary is: ", (rate * hours))

letter = input("Please input a letter: ")
print("The value of this letter is: ", ord(letter))
print("Your letter was: ", chr(ord(letter)))
"""
key = 2
message = input("Please enter a word: ")
encMessage = ""
unEncMessage = ""
for letter in message:
    lower = [ord('a'), ord('z')]
    upper = [ord('A'), ord('Z')]
    num = ord(letter)
    if ((lower[0] <= num) and (num <= lower[1])):
        num += key
        if (num > lower[1]):
            num = ((lower[0] - 1) + (num - lower[1]))
        encMessage += chr(num)
    elif ((upper[0] <= num) and (num <= upper[1])):
        num += key
        if (num > upper[1]):
            num = ((upper[0] - 1 )+ (num - upper[1]))
        encMessage += chr(num)
    else:
	print("input invalid")
        break;
print("The encrypted message is: ", encMessage)
for letter in encMessage:
    lower = [ord('a'), ord('z')]
    upper = [ord('A'), ord('Z')]
    num = ord(letter)
    if ((lower[0] <= num) and (num <= lower[1])):
        num -= key
        if (num < lower[0]):
            num = ((lower[1] + 1) - (lower[0] - num))
        unEncMessage += chr(num)
    elif ((upper[0] <= num) and (num <= upper[1])):
        num -= key
        if (num < upper[0]):
            num = ((upper[1] + 1) - (upper[0] - num))
        unEncMessage += chr(num)
    else:
	print("input invalid")
        break;
print("The unencrypted message is: ", unEncMessage)
