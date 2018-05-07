from math import sqrt

class Point(object):
    def __init__(self, x, y):
        self.x, self.y = x, y

    def desloca(self, dx, dy):
        self.x = self.x * dx
        self.y = self.y * dy
        print("({}, {})".format(self.x, self.y))

    def distancia(self, p2):
        dx = p2.x - self.x
        dy = p2.y - self.y
        return sqrt(dx*dx + dy*dy)

    @staticmethod
    def distanciaStatic(p1, p2):
        dx = p2.x - p1.x
        dy = p2.y - p1.y
        return sqrt(dx*dx + dy*dy)

p1 = Point(4, 8)
p2 = Point(6, 12)
p1.desloca(2, 2)

print(p1.distancia(p2))
print(Point.distanciaStatic(p1, p2))
