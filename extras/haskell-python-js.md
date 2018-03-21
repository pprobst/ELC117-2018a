# Programação Funcional: Haskell, Python e JavaScript.

### Este pequeno texto tem como objetivo realizar breve comparações entre os aspectos funcionais de Python e JavaScript -- linguagens multiparadigmas -- com Haskell, uma linguagem puramente funcional.

---

### Funções de Ordem Superior e Lambda

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

### *List Comprehension*

Certo, pudemos ver com simples exemplos que funções de ordem superior e lambda
funcionam de modo similar nas três linguagens vistas. Entretanto, como podemos
fazer uso de *list comprehension*?

*List comprehension*, em suma, é uma forma de gerar listas a partir de uma certa regra
definida pelo programador. Com exemplos esse conceito ficará bem claro.

Apesar de ser recente em JS (EcmaScript 7), Python suporta *list comprehension*
desde a versão 2.0; em Haskell, é algo intrínseco à linguagem.

Nos casos abaixo, usamos *list comprehension* para gerarmos uma lista que
contenha todas as idades no intervalo fechado entre 15 e 30 anos, a partir 
de uma lista de idades passada como parâmetro.

Haskell:
```Haskell
bet15and30 :: [Int] -> [Int]
bet15and30 lst = [x <- lst, x >= 15 && x <= 30]
> bet15and30 [1,2,10,50,30,20,15,16,60,29]
-- [30,20,15,16,29]
```

Python:
```Python
def betFifteenAndThirty(lst):
    return [x for x in lst if x >= 15 and x <= 30]
```

JavaScript:
```JavaScript
function betw(lst) {
    return [for (x of lst) if (x >= 15 && x <= 30) lst];
}
```

Veja mais em:

https://wiki.haskell.org/List_comprehension

http://www.secnetix.de/olli/Python/list_comprehensions.hawk

https://developer.mozilla.org/pt-BR/docs/Web/JavaScript/Reference/Operators/Array_comprehensions

