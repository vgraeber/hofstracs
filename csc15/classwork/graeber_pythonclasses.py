class student():
  def __init__(self, name, major, grades):
    self.name = name
    self.major = major
    self.gpa = self.getgpa(grades)

  def __str__(self):
    return f"{self.name}, {self.major} major, {self.gpa} GPA"

  def getgpa(self, grades):
    pointvals = []
    for i in grades:
      if (i >= 90):
        pointvals.append(4.0)
      elif (i >= 80):
        pointvals.append(3.0)
      elif (i >= 70):
        pointvals.append(2.0)
      elif (i >= 65):
        pointvals.append(1.0)
      else:
        pointvals.append(0.0)
    return round((sum(pointvals) / len(pointvals)), 2)

me = student("Vivian", "Comp sci", [100, 97, 82])
print(me)
