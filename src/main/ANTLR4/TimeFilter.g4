lexer grammar TimeFilter;
TIME : HOUR ':' MINORSECFIRST ':' MINORSECFIRST | HOUR ':' MINORSECFIRST
;



fragment HOUR : [0-1][0-9] | [2][0-3] ;
fragment MINORSECFIRST : [0-5][0-9] ;

// Everything else to be ignored
OTHER : . -> skip ;