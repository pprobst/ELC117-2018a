%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% T3 - Programação lógica em Prolog %%%
%%%        PEDRO PROBST MININI        %%%
%%%            2018-04-13             %%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% (1)
% Defina um predicado zeroInit(L) que é verdadeiro se L for uma
% lista que inicia com o número 0 (zero).

zeroInit(L) :-
    [H|_] = L,
    H is 0.

% (2)
% Defina um predicado has5(L) que é verdadeiro se L for uma lista
% de 5 elementos. Resolva este exercício sem usar um predicado aux.

has5(L) :- L = [_,_,_,_,_]. % posições com underline -> não importa o valor!
                            % o que importa nesse caso é o número de posições!

% (3)
% Defina um predicado hasN(L,N) que é verdadeiro se L for uma lista 
% de N elementos.

hasN([], 0).
hasN(L, N) :- length(L,N).

% (4)
% Defina um predicado potN0(N,L), de forma que L seja uma lista de 
% potências de 2, com expoentes de N a 0. 
% https://stackoverflow.com/questions/10202666/prolog-create-a-list

potN0(0, [1]).
potN0(N, L) :-
    N > 0,
    N1 is N-1,
    N2 is 2^N,
    potN0(N1, L2),
    L = [N2|L2].

% (5)
% Defina um predicado zipmult(L1,L2,L3), de forma que cada elemento 
% da lista L3 seja o produto dos elementos de L1 e L2 na mesma posição 
% do elemento de L3.

zipmult([], _, 0).
zipmult(_, [], 0).
zipmult(L1, L2, L3) :-
    [H1|T1] = L1,
    [H2|T2] = L2,
    ELEM is H1*H2,
    zipmult(T1, T2, L),
    L3 = [ELEM|L].

% (6)
% Defina um predicado potencias(N,L), de forma que L seja uma lista com 
% as N primeiras potências de 2, sendo a primeira 2^0 e assim por diante.

potencias(N, L) :- potenciasAux(N-N, N, L).

potenciasAux(LIM, LIM, []).
potenciasAux(N, LIM, L) :-
    N < LIM,
    N1 is N+1,
    N2 is 2^N,
    potenciasAux(N1, LIM, L2),
    L = [N2|L2].

% (7)
% Defina um predicado positivos(L1,L2), de forma que L2 seja uma lista só 
% com os elementos positivos de L1.

% (passado em aula!)
positivos([], []).
positivos([H|T], L) :- H > 0, positivos(T, Resto), L = [H|Resto].
positivos([H|T], L) :- H =< 0, positivos(T, L).

% (8)
% Considere que L1 e L2 sejam permutações de uma lista de elementos 
% distintos, sem repetições. Sabendo disso, defina um predicado 
% mesmaPosicao(A,L1,L2) para verificar se um elemento A está na mesma 
% posição nas listas L1 e L2. 

calcIndice([H|_], H, 0).
calcIndice([_|T], V, I) :-
    calcIndice(T, V, I1),
    I is I1+1.

mesmaPosicao(A, L1, L2) :-
    calcIndice(L1, A, POS1),
    calcIndice(L2, A, POS2),
    POS1 is POS2.

% (9)
% Dada uma lista de N alunos, deseja-se escolher NP alunos (NP < N) para 
% formar uma comissão. Para isso, defina um predicado comissao(NP,LP,C), 
% que permita gerar as possíveis combinações C com NP elementos da lista LP.

% OBS.: Honestamente, quebrei a cabeça com isso e não consegui fazer.
% Peguei a solução daqui: https://waa.ai/zLGA
% Entretanto, tentarei explicar do modo como entendi:
%
% - comissao(NP, LP, C):
%   > O caso mais simples e se for selecionado 0 elementos: neste caso,
%   C deve ser uma lista vazia!
%   > Se NP > 0, chamamos pegaResto com LP sendo a lista;
%   > pegaResto continuará uma vez por elemento H de LP.
%   > Assim ele "junta" os elementos da combinação em ordem, 
%     fazendo uso de backtracking em árvore (ver trace).
%
% - pegaResto(X, [_|L], T):
%   > C é [_|L] com todos os elementos após X.
%   > Ou seja, "percorre" a lista até que o Head seja X, portanto, 
%   se head(lista) == x, então tail(lista) == C (caso mais simples).

comissao(0, _, []).
comissao(NP, LP, C) :- 
    [H|T] = C,
    NP > 0,
    NP1 is NP - 1,
    pegaResto(H, LP, Resto),
    comissao(NP1, Resto, T).

pegaResto(X, [X|L], L).
pegaResto(X, [_|L], T) :- pegaResto(X, L, T).


% (10)
% (Adaptado de OBI2006-F1N1) Tem-se N azulejos 10cm x 10cm e, com eles, 
% deve-se montar um conjunto de quadrados de modo a utilizar todos os 
% azulejos dados, sem sobrepô-los. Inicialmente, deve-se montar o maior 
% quadrado possível; então, com os azulejos que sobraram, deve-se montar 
% o maior quadrado possível, e assim sucessivamente. Por exemplo, se forem 
% dados 31 azulejos, o conjunto montado terá 4 quadrados. Para resolver 
% este problema, você deverá definir um predicado azulejos(NA, NQ), de forma
% que NQ seja o número de quadrados que se deve montar com NA azulejos. 
% Dica: use os predicados sqrt e floor, pré-definidos em Prolog.

% Para resolvermos isso, temos de nos atentar a certo fato:
% -> Um quadrado de azulejos deve ter um número de azulejos tal que este
%    seja um quadrado perfeito.
% 
% O modus operandi para obtermos 4 quadrados de 31 azulejos é o seguinte:
% 1)  Extraímos a raiz de 31 => 5.56
% 2)  Fazemos o "floor" de 5.56 => 5
% 3)  Elevamos 5 ao quadrado => 5^2 => 25
%     O maior quadrado possível é de 25 azulejos.
% 4)  31 - 25 = 6; Ainda sobram 6 azulejos.
%     Neste caso, repetimos o processo acima para 6.
% 5)  Raiz de 6 => 2.449i
% 6)  Floor de 2.49 => 2
% 7)  2^2 = 4.
%     O maior quadrado possível é com 4 azulejos.
% 8)  6 - 4 = 2; Ainda sobram 2 azuejos.
%     Repetimos o processo.
% 9)  Raiz de 2 = 1.41
% 10) Floor de 1.41 = 1
% 11) 1^2 = 1
%     O maior quadrado possível é de 1 azulejo.
% 12) 2 - 1 = 1; Ainda sobra 1 azulejo.
% 13) Podemos fazer apenas mais um quadrado com um azulejo.
% ----------------------------------------------------------
% Resultado: 1 quadrado de 25 azulejos.
%            1 quadrado de 4 azulejos. 
%            2 quadrados de 1 azulejo.
%            -------------------------
% Total:     4 azulejos.

% Uma tradução direta do algoritmo acima pode ser vista abaixo:
azulejos(0, 0). % caso base: se tivermos 0 azulejos, obviamente não
                % poderemos formar um quadrado.

azulejos(NA, NQ) :-
    SQRT is sqrt(NA), % pega a raiz de NA
    INT is floor(SQRT), % pega a parte inteira de sqrt(NA)
    TAM_QUADRADO is INT^2, % tamanho do quadrado em nº de azulejos
    RESTO is NA-TAM_QUADRADO, % azulejos restantes para a próxima chamada
    azulejos(RESTO, NQ1),
    NQ is NQ1+1. % incrementa o número de quadrados
