%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%% T4b -   Show de Talentos          %%%
%%%%        PEDRO PROBST MININI        %%%
%%%%            2018-04-22             %%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%https://olimpiada.ic.unicamp.br/static/extras/obi2015/provas/ProvaOBI2015_f1i1.pdf
%------------------------------------------------------------------------------------%
% No tradicional Show de Talentos da escola os alunos podem apresentar-se para       %
% mostrar suas diversas habilidades. Vale tudo: música, drama, malabarismo,...       %
% Este ano, sete alunos (A, B, C, D, E, F e G) estão inscritos. Cada aluno se        %
% apresentará uma única vez, em um dos sete turnos do Show, numerados de 1 a 7.      % 
% As seguintes restrições devem ser obedecidas para decidir a ordem de apresentação: %
%                                                                                     %
% • A deve apresentar-se no turno 3 ou no turno 5.                                   %
% • F não pode se apresentar nem no turno 4 nem no turno 6.                          %
% • Se D se apresentar no turno 1, C deve se apresentar no turno 2.                  %
% • Se E se apresentar no turno 4, F deve se apresentar no turno 5.                  %
% • B deve se apresentar no turno imediatamente após o turno em que C se apresentar. %
% -----------------------------------------------------------------------------------%

regra1(A) :- A = [_, _, a, _, _, _, _].
regra1(A) :- A = [_, _, _, _, a, _, _].

regra2(A) :- not(nth0(3, A, f) ; nth0(5, A, f)).

regra3(A) :-
    nth0(Id, A, d),
    nth0(Ic, A, c),
    regra3Aux(Id, Ic).
regra3Aux(Id, _) :-
    ( Id =\= 0 ->
    true;
    false).
regra3Aux(0, Ic) :-
    ( Ic =\= 1 ->
    false;
    true).

regra4(A) :-
    nth0(Ie, A, e),
    nth0(If, A, f),
    regra4Aux(Ie, If).
regra4Aux(Ie, _) :- 
    ( Ie =\= 3 -> 
    true;
    false).
regra4Aux(3, If) :-
    ( If =\= 4 ->
    false;
    true).

regra5(A) :-
    nth0(Ic, A, c),
    Ib is Ic + 1,
    nth0(Ib, A, b).

% Permutação
perm([],[]).
perm(List, [H|Perm]) :- delete(H, List, Rest), perm(Rest, Perm).

delete(X, [X|T], T).
delete(X, [H|T], [H|NT]) :- delete(X, T, NT).

% Regras combinadas
apresentacoes(A) :-
    A = [_, _, _, _, _, _, _],
    Alunos = [a, b, c, d, e, f, g],
    perm(Alunos, A),
    regra1(A),
    regra2(A),
    regra3(A),
    regra4(A),
    regra5(A), !.


% Questão 9. 
% ----------
% Qual das alternativas abaixo é uma ordem válida para as apresentações?
% ?- apresentacoes([f,b,c,g,a,d,e]).
% ?- apresentacoes([f,d,e,g,a,c,b]).
% ?- apresentacoes([f,d,a,e,c,b,g]).
% ?- apresentacoes([c,b,a,g,e,f,d]).
% ?- apresentacoes([c,b,f,a,g,e,d]).
%
% CORRETA: apresentacoes([f,d,e,g,a,c,b]). 
