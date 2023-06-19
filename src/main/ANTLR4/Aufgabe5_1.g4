grammar Aufgabe5_1;

file: (functionDecl | varDecl)+ ;

varDecl
:  <assoc=left> type ID ('=' expr)? ';'
;
type: 'float' | 'int' | 'void' ; // user-defined types

functionDecl
:  <assoc=left> type ID '(' formalParameters? (') '| ')') block #FuncDecl// "void f(int x) {...}"
;
formalParameters
:  <assoc=left> formalParameter (',' formalParameter)*
;
formalParameter
:  <assoc=left> type ID
;

block: '{' stat* '}' ; // possibly empty statement block
stat: <assoc=left> block #IrrBlockBrackets
| <assoc=left> varDecl #IrrVarDec
//| 'if' expr 'then' stat ('else' stat)?
| <assoc=left> 'if (' expr ') ' (block|stat) ('else ' (block|stat))? #IfElse
| <assoc=left> 'return' expr? ';' #Return
| <assoc=left> expr '=' expr ';' #IrrAssigment// assignment
| <assoc=left> expr ';' #IrrCall// func call
;

expr: <assoc=left> expr '[' expr ']' #IrrArray// array index like a[i], a[i][j]
| <assoc=left> '-' expr #IrrMinus// unary minus
| <assoc=left> '!' expr #IrrNeg// boolean not
| <assoc=left> expr ('*'|'/') expr #IrrMultDiv
| <assoc=left> expr ('+'|'-') expr #IrrAddSub
| <assoc=left> expr ('!='|'<'|'>'|'>='|'<='|'==') expr #IrrComp
| <assoc=left> ID '(' exprList? (') '| ')') #FuncCall// func call like f(), f(x), f(1,2)
| <assoc=left> ID #IrrId// variable reference
| <assoc=left> INT #IrrInt
| <assoc=left> '(' expr ')' #IrrBrac
;
exprList :  <assoc=left> expr (',' expr)* ; // arg list

ID : [a-zA-Z]+ [0-9]?;
INT : [0-9]+ ;

WS :   (' ' | '\t' | '\r' | '\n' ) -> skip;