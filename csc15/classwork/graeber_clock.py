Python 3.11.5 (tags/v3.11.5:cce6ba9, Aug 24 2023, 14:38:34) [MSC v.1936 64 bit (AMD64)] on win32
Type "help", "copyright", "credits" or "license()" for more information.

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py
Traceback (most recent call last):
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 23, in <module>
    stopwatch()
TypeError: stopwatch() missing 1 required positional argument: 'form'

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py
Traceback (most recent call last):
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 23, in <module>
    stopwatch("24hr")
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 18, in stopwatch
    tr.wrtie(curTime, font=('times new roman', 50, bold))
AttributeError: module 'turtle' has no attribute 'wrtie'. Did you mean: 'write'?

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py
Traceback (most recent call last):
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 23, in <module>
    stopwatch("24hr")
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 18, in stopwatch
    tr.write(curTime, font=('times new roman', 50, bold))
NameError: name 'bold' is not defined

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py
Traceback (most recent call last):
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 23, in <module>
    stopwatch("24hr")
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 19, in stopwatch
    tr.hideTurtle()
AttributeError: module 'turtle' has no attribute 'hideTurtle'. Did you mean: 'hideturtle'?

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py
Traceback (most recent call last):
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 23, in <module>
    stopwatch("24hr")
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 18, in stopwatch
    tr.write(curTime, center, font=('times new roman', 50, 'bold'))
NameError: name 'center' is not defined
>>> 
= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py

= RESTART: \\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py
Traceback (most recent call last):
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 34, in <module>
    stopwatch("12hr")
  File "\\wsl.localhost\Ubuntu\home\vgraeber\hofstracs\csc15\classwork\graeber_nested.py", line 32, in stopwatch
    tr.clear()
  File "<string>", line 8, in clear
  File "C:\Users\Vivian\AppData\Local\Programs\Python\Python311\Lib\turtle.py", line 2644, in clear
    self._update()
  File "C:\Users\Vivian\AppData\Local\Programs\Python\Python311\Lib\turtle.py", line 2661, in _update
    self._update_data()
  File "C:\Users\Vivian\AppData\Local\Programs\Python\Python311\Lib\turtle.py", line 2647, in _update_data
    self.screen._incrementudc()
  File "C:\Users\Vivian\AppData\Local\Programs\Python\Python311\Lib\turtle.py", line 1293, in _incrementudc
    raise Terminator
turtle.Terminator
