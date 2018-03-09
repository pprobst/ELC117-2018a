# **(((Scheme)))**
## _Do básico ao intermediário_
---
### Pedro Probst Minini, Ciência da Computação, UFSM
#
![](wizard.jpg)

---

## 1. Introdução
>Computational processes are abstract beings that inhabit computers. As they evolve, processes manipulate other abstract things called data. The evolution of a process is directed by a pattern of rules called a program. People create programs to direct processes. In effect, we conjure the spirits of the computer with our spells.
>
>― Harold Abelson

_Scheme_ é uma linguagem multiparadigma e um dos principais dialetos de LISP, uma família de linguagens com foco em programação funcional e cálculo lambda. Scheme foi criada na década de 70 pelos pesquisadores Guy L. Steele e Gerald Jay Sussman (MIT). Seguindo uma filosofia minimalista, Scheme pode ser considerada como uma linguagem _educacional_, e por um bom tempo foi a linguagem utilizada em disciplinas introdutórias de Ciência da Computação no MIT.

Uma linguagem de programação poderosa é mais do que simplesmente um meio de instruir o computador a executar determinada tarefa. A linguagem também serve para organizar e modelar nossas ideias sobre processos. Toda linguagem poderosa tem três mecanismos para realizar isso:

* **expressões primitivas**, que representam as mais simples entidades nas quais a linguagem está interessada;
* **meios de combinação**, pelo qual elementos compostos são construídos a partir de elementos mais simples;
* **meios de abstração**, pelo qual elementos compostas podem ser nomeados e manipulados como unidades.

Neste tutorial, irei exibir os principais elementos de Scheme -- uma linguagem simples e muitas vezes estranha. Não focarei nos elementos mais teóricos da linguagem, mas sim nos elementos práticos dela.

### 1.1 Instalação
Para programar em Scheme, antes é preciso instalar as dependências necessárias. Neste tutorial, utilizarei o MIT/GNU Scheme, uma implementação completa de Scheme com interpretador, compilador, debugger, etc. Os binários podem ser encontrados em https://www.gnu.org/software/mit-scheme/
ou no repositório de sua distribuição GNU/Linux.

Caso julgar a implementação MIT/GNU insuficientemente prática, você pode utilizar Racket com a DrRacket IDE e o pacote _sicp_ (https://docs.racket-lang.org/sicp-manual/).

Os arquivos em Scheme têm a extensão _.scm_. Caso tiver instalado MIT/GNU Scheme, digite o comando `scheme < nome_do_arquivo.scm` para executar um programa pelo terminal.

## 2. Os básicos
### 2.1 Manipulação de expressões
Na maior parte deste tutorial, estaremos manipulando números, um tipo de _expressão primitiva_. Abra o interpretador Scheme digitando `scheme` no terminal.

Caso você digitar um número qualquer, como por exemplo 166, o interpretador retornará `166` como esperado. Naturalmente, você pode combinar expressões para gerar resultados mais interessantes. Como Scheme utiliza notação de prefixo, o operador precede os operandos:

```scheme
(+ 133 220)
353
(- 344 567)
-233
(* 4 5)
20
(/ 30 5)
6
(+ 2.6 0.3)
2.9
(* 3 4 5 6 7 8)
20160
```
Uma vantagem da notação de prefixo é que você pode aninhar expressões de um modo bastante direto:

```scheme
(+ (* 3 5) (- 10 6))
19
(/ 53.0 (+ 65 (* 2 2)))
.7681159420289855
```
É uma boa prática alterar a forma do código quando necessário:
```scheme
(+ (* 3
      (+ (* 2 4)
         (+ 3 5)))
   (+ (- 10 7)
      6))
57
```

### 2.2 A palavra-chave _define_
Em Scheme, utilizamos

---

## Referências
_Structure and Interpretation of Computer Programs, 2nd ed._, Harold Abelson and Gerald Jay Sussman with Julie Sussman

_The Little Schemer, 4th ed._, Daniel P. Friedman & Matthias Felleisen
