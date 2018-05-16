# Polimorfismo em Python
##### Adaptado de: https://www.digitalocean.com/community/tutorials/how-to-apply-polymorphism-to-classes-in-python-3

---

### Introdução
Etimologicamente, *polimorfismo* é a capacidade que algo tem de assumir
diversas formas. No contexto de programação, isso quer dizer basicamente o
seguinte:
> *Polimorfismo* descreve o conceito de que objetos de diferentes tipos podem
> ser acessados pela mesma interface. O polimorfismo pode ser aplicado por meio
> de hereditariedade, com subclasses fazendo uso dos métodos da classe base ou
> sobrescrevendo-os.

Diferentemente de Java, Python utiliza *duck typing*. Isso quer dizer, por
exemplo, que ao invés de nos perguntarmos se um objeto *é um pato*, a pergunta
correta seria se o objeto *faz "quack" como um pato e anda como um pato*. O
polimorfismo em Python, portanto, se baseia em semelhanças.

Enfim, mãos à obra:

### Criando Classes Polimórficas
Duas classes distintas e dois objetos distintos serão criados como exemplo.
Cada uma dessas classes  precisa de uma interface comum, então criaremos
métodos que são distintos mas possuem o mesmo nome.

```python
class Tubarao():
    def nadar(self):
        print("O tubarão está nadando.")

    def nadar_para_tras(self):
        print("O tubarão não pode nadar para trás, mas pode afundar para trás")

    def esqueleto(self):
        print("O esqueleto do tubarão é feito de cartilagem.")

class PeixePalhaco():
    def nadar(self):
        print("O peixe-palhaco está nadando.")

    def nadar_para_tras(self): 
        print("O peixe-palhaco pode nadar para trás.")

    def esqueleto(self):
        print("O esqueleto do peixe-palhaco é feito de osso.")
```

Agora, vamos instanciar essas classes em dois objetos:

```python
...
sammy = Tubarao()
sammy.esqueleto()

casey = PeixePalhaco()
casey.esqueleto()
```

Quando rodarmos o programa, o output será o seguinte:

```
O esqueleto do tubarão é feito de cartilagem.
O esqueleto do peixe-palhaço é feito de osso.
```

Como agora temos dois objetos que fazem uso de uma interface comum, nós podemos
usar os dois objetos do mesmo modo, indepentendemento dos seus tipos
individuais.

### Polimorfismo com Métodos de Classe
Podemos mostrar como Python pode usar cada uma dessas classes distintas do
mesmo modo com o código abaixo.

```python
...
sammy = Tubarao() # objeto sammy da classe Tubarao

casey = PeixePalhaco() # objeto casey da classe PeixePalhaco

for peixe in (sammy, casey):
    peixe.nadar();
    peixe.nadar_para_tras();
    peixe.esqueleto();
```

Quando rodamos o programa, o output é o seguinte:

```
O tubarão está nadando.
O tubarão não pode nadar para trás, mas pode afundar para trás.
O esqueleto do tubarão é feito de cartilagem.
O peixe-palhaço está nadando.
O peixe-palhaço pode nadar para trás.
O esqueleto do peixe-palhaço é feito de osso.
```

Isso mostra que Python usa esses métodos sem se importar sobre qual tipo de
classe cada um desses objetos é. Ou seja, usando esses métodos de um modo
polimórfico.

### Polimorfismo com uma Função
Em Python também podemos fazer Polimorfismo com funções. No caso, criaremos uma
função que pode receber qualquer objeto.

```python
...
def no_pacifico(peixe):
    peixe.nadar()

sammy = Tubarao()

casey = PeixePalhaco()

no_pacifico(sammy)
no_pacifico(casey)
```

Output:

```
O tubarão está nadando.
O peixe-palhaço está nadando.
```

Ainda que passamos um objeto aleatório `fish` na função `no_pacifico()` quando
a definimos, nós ainda pudemos usá-la para instâncias das classes `Tubarao` e
`PeixePalhaco`. O objeto `casey` chamou o método `nadar()` definido na classe
`PeixePalhaco`, e o objeto `sammy` chamou o método `nadar()` definido na classe
`Tubarao` .
