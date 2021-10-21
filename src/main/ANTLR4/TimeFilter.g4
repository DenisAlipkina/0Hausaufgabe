lexer grammar TimeFilter;
TIME : HOUR ':' MINORSEC (':' MINORSEC)?
;



fragment HOUR : [0-1][0-9] | [2][0-3] ;
fragment MINORSEC : [0-5][0-9] ;

// Everything else to be ignored
OTHER : . -> skip ;