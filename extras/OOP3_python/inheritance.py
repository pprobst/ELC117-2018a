class Person(object):
    def __init__(self):
        print("Construtor de Person")
        self.name = "Fulano"

    def getName(self):
        return self.name

    def setName(self, name):
        self.name = name

class Student(Person):
    def __init__(self):
        super().__init__() # permite acessar todos os métodos da classe pai
        print("Construtor de Student")
        self.course = "CC"

    def testName(self, c):
        return self.name[0] == c

class PhDStudent(Student):
    def __init__(self):
        super().__init__()
        print("Construtor de PhDStudent")

p = Person()
s = Student()
phd = PhDStudent()
lis = []

lis.append(p)
lis.append(s)
lis.append(phd)

s.setName("Beltrano")

for e in lis:
    print(e.getName())

print(s.testName('B')) # True
print(isinstance(phd, Person)) # True
print(isinstance(Person, PhDStudent)) # False
