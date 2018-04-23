%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% T4a -    CD Independente          %%%
%%%        PEDRO PROBST MININI        %%%
%%%            2018-04-21             %%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% -------------------------------------------------------------------------------
% Uma banda formada por alunos e alunas da escola está gravando um CD com       %
% exatamente sete músicas distintas – S, T, V, W, X, Y e Z. Cada música ocupa   %
% exatamente uma das sete faixas contidas no CD. Algumas das músicas são        %
% sucessos antigos de rock; outras são composições da própria banda.            %
%                                                                               % 
% As seguintes restrições devem ser obedecidas:                                 %
%                                                                               %
% * S ocupa a quarta faixa do CD.                                               %
%                                                                               %
% * Tanto W como Y precedem S no CD (ou seja, W e Y estão numa faixa que é      %
%   tocada antes de S no CD).                                                   %
%                                                                               %
% * T precede W no CD (ou seja, T está numa faixa que é tocada antes de W).     %
%                                                                               %
% * Um sucesso de rock ocupa a sexta faixa do CD.                               %
%                                                                               %
% * Cada sucesso de rock é imediatamente precedido no CD por uma composição     %
%   da banda (ou seja, no CD cada sucesso de rock toca imediatamente após uma   %
%   composição da banda).                                                       %
%                                                                               %
% * Z é um sucesso de rock.                                                     %
%                                                                               %
% -------------------------------------------------------------------------------

% OBS.: solução inspirada por:  
% https://github.com/AndreaInfUFSM/elc117-2018a/blob/master/trabalhos/t4/obi-estacionamento.pl

% S ocupa a quarta faixa do CD
regra1(CD) :- CD = [_, _, _, s, _, _, _].

% W e Y precedem S
regra2(CD) :-
    nth0(Iw, CD, w),
    nth0(Iy, CD, y),
    nth0(Is, CD, s),
    Iw < Is,
    Iy < Is.

% T precede W no CD
regra3(CD) :-
    nth0(It, CD, t),
    nth0(Iw, CD, w),
    It < Iw.

% Um sucesso de rock está na sexta posição (índice 5)
regra4(CD) :-
    faixa(X, rock),
    nth0(5, CD, X).
    
% Cada sucesso de rock é precedido por uma composição da banda
regra5(CD) :-
    faixa(X, rock),
    faixa(Y, banda),
    nextto(Y, X, CD).

% Z é um sucesso de rock
faixa(z, rock).
faixa(_, banda).

% Permutação
perm([],[]).
perm(List, [H|Perm]) :- delete(H, List, Rest), perm(Rest, Perm).

delete(X, [X|T], T).
delete(X, [H|T], [H|NT]) :- delete(X, T, NT).

% Regras combinadas
faixas(CD) :-
    CD = [_, _, _, _, _, _, _],
    Musicas = [s, t, v, w, x, y, z],
    perm(Musicas, CD),
    regra1(CD),
    regra2(CD),
    regra3(CD),
    regra4(CD),
    regra5(CD).


% Questão 1. 
% ----------
% Qual das seguintes alternativas poderia ser a ordem das músicas no CD, da 
% primeira para a sétima faixa? 
%
% ?- faixas([t, w, v, s, y, x, z]).
% ?- faixas([v, y, t, s, w, z, x]).
% ?- faixas([x, y, w, s, t, z, s]).
% ?- faixas([y, t, w, s, x, z, v]).
% ?- faixas([z, t, x, w, v, y, s]).

% CORRETA: faixas([y, t, w, s, x, z, v]).
