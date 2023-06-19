grammar Aufgabe3_2;

prog : stat | prog stat;

stat : expr N | N;


expr :
    term
    | term (('+'|'-') term)+
    ;

term :
    fact
    | fact (('*'|'/') fact)+
    ;

fact
    : Number
    | '(' expr ')'
    | ('+'|'-') fact
    ;

Number : [0-9]+;

N : '\n';

WS :   (' ' | '\t' | '\r') -> skip;