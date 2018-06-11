import random

class Carta():
    naipes = ["Espadas", "Paus", "Copas", "Ouros"]
    valores = ["Ás", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete",
               "Dama", "Rei"];

    def __init__(self, valor, naipe):
        self.valor = valor;
        self.naipe = naipe;

    def __str__(self):
        return("{} de {}".format(Carta.valores[self.valor], Carta.naipes[self.naipe]))

class Baralho():
    def __init__(self):
        self.cartas = []
        for naipe in range(4):
            for valor in range(13):
                carta = Carta(valor, naipe)
                self.cartas.append(carta)

    def mostra_cartas(self):
        for carta in self.cartas:
            print(carta)

    def add_carta(self, carta):
        self.cartas.append(carta)

    def remove_carta(self, carta):
        self.cartas.remove(carta)

    def pop_carta(self, i=-1): # i = -1 --> último índice
        return self.cartas.pop(i)

    def ordena(self):
        self.cartas.sort()

    def embaralha(self):
        random.shuffle(self.cartas)

    # retira n cartas do baralho para a mão
    def move_cartas(self, mao, n):
        for i in range(n):
            mao.add_carta(self.pop_carta())

class Mao(Baralho):
    def __init__(self, ato=''):
        self.cartas = []
        self.ato = ato

if __name__ == '__main__':
    baralho = Baralho()
    baralho.embaralha()

    print("Cartas do baralho:")
    print(baralho.mostra_cartas())

    mao = Mao()
    baralho.move_cartas(mao, 3)

    print("\nCartas da mão:")
    print(mao.mostra_cartas())
