grammar Aufgabe3_1;

prog : prog stat |  stat ;


stat : expr N | N;


expr :
    term
    | expr ('+'|'-') term
    ;

term :
    fact
    | term ('*'|'/') fact
    ;

fact
    :    Number
    |    '(' expr ')'
    ;

Number : [0-9]+;

N : '\n';

WS :   (' ' | '\t' | '\r') -> skip;