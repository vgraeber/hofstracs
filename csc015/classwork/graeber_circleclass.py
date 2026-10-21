# Author: Vivian Graeber
# Date: 11/15/23
# Description: Python class practice

class Circle():
  def __init__(self, radius):
    self.radius = radius
    self.diameter = self.getD(radius)
    self.area = self.getA(radius)
    self.circumference = self.getC(radius)

  def __str__(self):
    return f"This circle has a radius of {self.radius}, a diameter of {self.diameter}, an area of {self.area}, and a circumference of {self.circumference}."

  def getD(self, r):
    return (2 * r)

  def getA(self, r):
    return (str(r ** 2) + 'π')

  def getC(self, r):
    return (str(2 * r) + 'π')
