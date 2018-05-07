from math import pi

class Circle(object):
    def __init__(self, x=0.0, y=0.0, r=0.0):
        self.x, self.y, self.r = x, y, r

    def area(self):
        return pi*self.r**2

    def setRadius(self, R):
        self.r = R


#c1 = Circle(2.0, 3.0, 2.0)
#print(c1.area())
#jc1.setRadius(4.0)
#print(c1.area())
