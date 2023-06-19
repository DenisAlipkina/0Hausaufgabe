lexer grammar HalsteadLexer;

IGNORE: CLOSING_BRACKETS | INCLUDE_LINES | 'do' | ':' | LINE_COMMENT | COMMENT;
OPERATOR: SCSPEC | TYPE_QUAL | RESERVED | OPERATORS;
OPERAND: IDENTIFIER | TYPESPEC | CONSTANT;


fragment TYPESPEC : 'bool' | 'char' | 'double' | 'float' | 'int' | 'long' | 'short' | 'signed' | 'unsigned' | 'void';
fragment IDENTIFIER : ([a-zA-Z]|[0-9]|'_')+ ;
fragment CONSTANT : STRING |  DIDIGTS | CHAR;
fragment CHAR :'\'' ([a-zA-Z]|[0-9]|'+'|'-'|'/'|'*'|'\n'|'\t'|'\r'|'\\0') '\'';
fragment DIDIGTS : [0-9]+ ('.'[0-9]* (('e'|'E') '-'? [0-9]+)? )?;
fragment STRING: '"' (ESC|.)*? '"' ; // match single digit
//fragment CHAR:  ; // match single digit
fragment ESC : '\\"' | '\\\\' ; // 2-char sequences \" and \\




fragment SCSPEC : 'auto' | 'extern' | 'inline' | 'register' | 'static' | 'typedef' | 'virtual' | 'mutable' ;
fragment TYPE_QUAL : 'const' | 'friend' | 'volatile' ;
fragment RESERVED : 'asm' | 'break' | 'case' | 'class' | 'continue' | 'default' | 'delete' | 'while' ' '? '(' | 'else'
                    | 'enum' | 'for' ' '? '(' | 'goto' | 'if' ' '? '(' | 'new' | 'operator' | 'private' | 'protected' | 'public'
                    | 'return' | 'sizeof' | 'struct' | 'switch' ' '? '(' | 'this' | 'union' | 'namespace' | 'using' | 'try'
                    | 'catch' | 'throw' | 'const_cast' | 'static_cast' | 'dynamic_cast' | 'reinterpret_cast' | 'typeid'
                    | 'template' | 'explicit' | 'true' | 'false' | 'typename';
fragment OPERATORS: '!' | '!=' | '%' | '%=' | '&' | '&&' | '&=' | '(' | '*' | '*=' | '+' | '++' | '+=' | ',' | '-'
                    | '--' | '-=' | '->' | '...' | '/' | '/=' | '::' | '<' | '<<' | '<<=' | '<=' | '==' | '>' | '>='
                    | '>>' | '>>=' | '?' | '[' | '^' | '^=' | '{' | '||' | '=' | '~' | ';';





fragment CLOSING_BRACKETS:')' | '}' | ']';
fragment INCLUDE_LINES:'#include' .*? '>';
fragment LINE_COMMENT : '//' .*? '\r'? '\n' ; // Match "//" stuff '\n'
fragment COMMENT : '/*' .*? '*/' ; // Match "/*" stuff "*/"






// Everything else to be ignored
OTHER : . -> skip ;