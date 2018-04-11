%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% T3 - Programação lógica em Prolog %%%
%%%        PEDRO PROBST MININI        %%%
%%%            2018-04-11             %%%
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
