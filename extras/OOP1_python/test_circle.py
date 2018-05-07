from circle import Circle

c1 = Circle()
c2 = Circle()

print("Area C1: {}".format(c1.area()))
print("Area C2: {}".format(c2.area()))

c1.r = 5.0
print(c1.r)

circulos = []
for i in range(10):
    circulos.append(Circle())

print(circulos[0].area())
