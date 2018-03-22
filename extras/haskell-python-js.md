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

Agora, vamos usar *filter* para filtrarmos todas as strings de uma lista que
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

Outra função muito usada é a *Fold* ou *Reduce*. Como o próprio nome implica,
ela reduz uma lista a um único valor, que é o procurado. Nos exemplos a seguir,
buscamos pelo elemento de maior valor de uma lista.

Haskell:
```Haskell
maiorValor :: [Int] -> Int
maiorValor lst = foldr max (head lst) lst
> maiorValor [1,2,10,5,-4,0,3]
-- 10

-- obs: (head lst) é apenas o primeiro valor a ser comparado 
-- em relação à lista
```

Python:
```Python
from functools import reduce

def maiorValor(lst):
    return reduce(lambda a,b: a if (a > b) else b, lst)
```

JavaScript:
```JavaScript
function maiorValor(lst) {
    return lst.reduce((a, b) => {
        if (a > b) return a
        else return b
    });
}
```

Obviamente há metodos melhores para calcular o valor máximo de uma lista,
entretanto quis mostrar o potencial de *reduce* mais claramente. Vendo os
códigos acima, você deve estar se perguntando por que o *reduce* de Haskell
possui um nome diferente. Há uma boa e extensa explicação sobre as funções
*fold* em https://wiki.haskell.org/Fold.

Leia mais sobre *reduce* em Python e JS respectivamente em
https://docs.python.org/3/library/functools.html#functools.reduce e 
https://developer.mozilla.org/pt-BR/docs/Web/JavaScript/Reference/Global_Objects/Array/Reduce

### *List Comprehension*

Certo, pudemos ver com simples exemplos que funções de ordem superior e lambda
funcionam de modo similar nas três linguagens vistas. Entretanto, como podemos
fazer uso de *list comprehension*?

*List comprehension*, em suma, é uma forma de gerar listas a partir de uma certa regra
definida pelo programador. Com exemplos esse conceito ficará bem claro.

Apesar de ser recente em JS (ECMAScript 7), Python suporta *list comprehension*
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
    return [x for x in lst if (x >= 15 and x <= 30)]
```

JavaScript:
```JavaScript
function betFifteenAndThirty(lst) {
    return [for (x of lst) if (x >= 15 && x <= 30) lst];
}
// obs: não funciona na maioria dos browsers.
```

Veja mais em sobre *list comprehension* em:

https://wiki.haskell.org/List_comprehension

http://www.secnetix.de/olli/Python/list_comprehensions.hawk

https://developer.mozilla.org/pt-BR/docs/Web/JavaScript/Reference/Operators/Array_comprehensions

