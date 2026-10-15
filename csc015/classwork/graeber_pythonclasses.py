class Student():
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

class Document():
  def __init__(self, title, authors, type, pubyear, publisher):
    self.title = title
    self.authors = self.getauthors(authors)
    self.type = type
    self.pubyear = pubyear
    self.publisher = publisher

  def __str__(self):
    return f"{self.title}, a {self.type} created by {self.authors} and published by {self.publisher} in {self.pubyear}."

  def getauthors(self, authors):
    allAuthors = ""
    if (len(authors) > 2):
      for i in range(len(authors) - 1):
        allAuthors += authors[i]
        allAuthors += ", "
      allAuthors += "and " + authors[-1]
    elif (len(authors) == 2):
      allAuthors += authors[0] + " and " + authors[1]
    else:
      allAuthors += authors[0]
    return allAuthors

goodbook = Document("Good Omens: the Nice and Accurate Prophecies of Agnes Nutter, Witch", ["Neil Gaiman", "Terry Pratchett"], "fantasy novel", "2006", "New York: William Morrow")
print(goodbook)
