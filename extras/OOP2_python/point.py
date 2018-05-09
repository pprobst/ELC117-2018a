from math import sqrt
from itertools import combinations

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

n = int(input("Número de pontos: "))
pts = []

for i in range(n):
    x = i*4
    y = x*2
    pts.append(Point(x, y))

'''
Ineficiente! Elementos já comparados são comparados novamente
para cada valor de i...

for i in pts:
    for j in pts:
        if (i != j):
            dist = Point.distanciaStatic(i, j)
            #dist = i.distancia(j)
            print("Distância entre ({}, {}) e ({}, {}) = {}"
                  .format(i.x, i.y, j.x, j.y, dist))
'''

for i, j in combinations(pts, 2):
    dist = Point.distanciaStatic(i, j)
    #dist = i.distancia(j)
    print("Distância entre ({}, {}) e ({}, {}) = {}"
          .format(i.x, i.y, j.x, j.y, dist))

