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

has5(L) :- L = [_,_,_,_,_].

% (3)
% Defina um predicado hasN(L,N) que é verdadeiro se L for uma lista 
% de N elementos.

hasN([], 0).
hasN(L, N) :- length(L,N).
