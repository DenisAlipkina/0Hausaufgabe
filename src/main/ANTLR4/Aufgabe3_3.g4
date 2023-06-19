grammar Aufgabe3_3;

prog : stat+ ;


stat : expr N | N;


expr :
    Number
    | expr '+' expr
    | expr '-' expr
    | expr '*' expr
    | expr '/' expr
    | '(' expr ')'
    ;

Number : [0-9]+;

N : '\n';

WS :   (' ' | '\t' | '\r') -> skip;