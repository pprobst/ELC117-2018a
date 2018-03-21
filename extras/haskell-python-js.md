# Programação Funcional: Haskell, Python e JavaScript.

### Este pequeno texto tem como objetivo realizar breve comparações entre os aspectos funcionais de Python e JavaScript -- linguagens multiparadigmas -- com Haskell, uma linguagem puramente funcional.

---

Começaremos com uma função bem básica: a *squareMe*. Essa função recebe uma lista qualquer (usando o termo genericamente) como argumento e eleva ao quadrado todos os seus elementos.

Haskell:
```Haskell
squareMe :: [Int] -> [Int]
squareMe lst = map (\x ->  x*x) lst
> squareMe [1,2,3,4]
-- [1,4,9,16]
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

Agora, vamos usar *filter* para filtrarmos todas as strings de uma lista de
contém espaços:

Haskell:
```Haskell
filterSpace :: [String] -> [String]
filterSpace lst = filter (' ' `elem`) lst
> filterSpace ["Kurapika", "Leorio", "Gon Freecss", "Killua Zoldyck"]
-- ["Gon Freecss","Killua Zoldyck"]
```

Python:
```Python
def filterSpace(lst):
    return list(filter(lambda x: ' ' in x, lst))
```

JavaScript:
```JavaScript
function filterSpace(lst) {
    return lst.filter(x => x.includes(' '));
}
```

Note como `elem`, `in` e `includes` têm uso similar nos três códigos: todos
checam se *algo* está contido em *outra coisa*.
