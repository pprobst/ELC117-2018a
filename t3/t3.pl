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

