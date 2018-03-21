# Programação Funcional: Haskell, Python e JavaScript.

### Este pequeno texto tem como objetivo realizar breve comparações entre os aspectos funcionais de Python e JavaScript -- linguagens multiparadigmas -- com Haskell, uma linguagem puramente funcional.

---

Começaremos com uma função bem básica: a *squareMe*. Essa função recebe uma lista qualquer (usando o termo genericamente) como argumento e eleva ao quadrado todos os seus elementos.

Haskell:

```Haskell
squareMe :: [Int] -> [Int]
squareMe lst = map (\x ->  x*x) lst
```

Python:
```Python
def squareMe(lst):
    return list(map(lambda x: x*x, lst))
```

JavaScript:
```JavaScript
function squareMe(lst) {
    return lst.map(x => x*x);
}
```

Note que há duas coisas em comum nos três códigos, que fazem absolutamente a mesma coisa: *map* e o uso de *lambda*.

*Map* nada mais é do que um modo de executar *alguma coisa* sobre uma lista qualquer. *Alguma coisa* normalmente é uma função, que neste caso simplesmente eleva um número *x* pertencente à lista ao quadrado.

*Lambda* é literalmente a aplicação de uma função matemática sobre um valor. Em programação, funções lambda são comumente chamadas de funções *anônimas*, já que elas normalmente não são nomeadas. Em programas funcionais, o uso de lambda é específico: normalmente utilizamos quando queremos executar determinada tarefa em apenas um lugar no código.

Nos códigos acima, podemos simplificar da seguinte maneira:
* **Map**: faz *algo* em todos os elementos de uma lista;
* **Lambda**: define esse *algo* para todos os elementos *x* da lista na qual *Map* está agindo.

Note as diferenças de sintaxe no uso de *lambda*:

```
(\x -> x*x)        Haskell
(lambda x: x*x)    Python
(x => x*x)         JavaScript
```
