grammar Aufgabe4_2;

prog : stat+
    ;


stat : e=expr N #ExprNewLine
    | <assoc=right> VAR '=' a=expr N #Initvar
    | 'clear' N #Clear
    | N #NewLine
    ;


expr : <assoc=right> expr POW expr #Pow
    | <assoc=left> expr op=(MUL|DIV) expr #MulDiv
    | <assoc=left> expr op=(ADD|SUB) expr #AddSub
    | <assoc=left> expr op=(EQUAL|BIGER|SMALLER) expr #Compare
    | <assoc=left> expr '?' expr ':' expr #ConExpr
    | <assoc=left> VAR #Var
    | '(' expr ')' #Brackets
    | Number #int
    ;

Number : [0-9]+;

POW : '^' ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
EQUAL : '==' ;
BIGER : '>' ;
SMALLER : '<' ;
VAR : [a-zA-Z]+ ;

N : '\n'| '\r';

WS :   (' ' | '\t' ) -> skip;