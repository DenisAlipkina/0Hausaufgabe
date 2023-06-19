grammar Aufgabe4_3;

@header {
import java.util.*;
}

@parser::members {
    ///** "memory" for our calculator; variable/value pairs go here
    Map<String, Integer> memory = new HashMap<String, Integer>();
    int result = 0;
    int eval(int left, int op, int right) {
        switch ( op ) {
            case EQUAL : result = left == right ? 1 : 0; return result;
            case BIGER : result = left > right ? 1 : 0; return result;
            case SMALLER : result = left < right ? 1 : 0; return result;
            case POW : result = (int) Math.pow(left, right); return result;
            case MUL : result = left * right; return result;
            case DIV : result = left / right; return result;
            case ADD : result = left + right; return result;
            case SUB : result = left - right; return result;
        }
        return 404;
    }

    int conExpr(int expr, int expr_a, int expr_b) {
        System.out.println(expr);
        System.out.println(expr_a);
        System.out.println(expr_b);
        int tmp = expr == 1 ? expr_a : expr == 0 ? expr_b : 0;
        result = tmp;
        return tmp;
    }
    int getVar(String key) {
        if(memory.containsKey(key)) {
            result = memory.get(key);
            return result;
        } else {
            result = 0;
            return result;
        }
    }
    void init(String key, int value) {
        result = 0;
        memory.put(key, value);
    }
}

prog : stat+
    ;


stat returns [int v]:
    e=expr N {$v = ($e.v);}
    //| <assoc=left>'if' '('a=expr')' b=stat 'else' c=stat{conExpr($a.v, $b.v, $c.v);}
    | <assoc=left> VAR '=' a=expr N {init($VAR.getText(), $a.v);}
    | 'clear' N {result = 0; $v = 0; memory = new HashMap<String, Integer>();}
    | N
    ;


expr returns [int v] :
    '(' e=expr ')' {$v = $e.v;}
    | <assoc=right> a=expr POW b=expr {$v = eval($a.v, POW, $b.v);}
    | <assoc=left> a=expr op=(MUL|DIV) b=expr {$v = eval($a.v, $op.type, $b.v);}
    | <assoc=left> a=expr op=(ADD|SUB) b=expr {$v = eval($a.v, $op.type, $b.v);}
    | <assoc=left> a=expr op=(EQUAL|BIGER|SMALLER) b=expr {$v = eval($a.v, $op.type, $b.v);}
    | <assoc=left> a=expr '?' b=expr ':' c=expr {$v = conExpr($a.v, $b.v, $c.v);}
    | <assoc=left> VAR {$v = getVar($VAR.getText());}
    | Number {$v = $Number.int;}
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
VAR : [a-zA-Z]+ | 'i' [a-hj-zA-HJ-Z] [a-zA-Z]* | 'els' [a-df-zA-DF-Z] [a-zA-Z]*;

N : '\n'| '\r' | 'n';

WS :   (' ' | '\t' ) -> skip;









/*prog : stat+
    ;


stat : expr {print('\n')}
    ;


expr : <assoc=left> term reststrich
    ;

reststrich : ADD {print('+')} term reststrich
    | SUB {print('-')} term reststrich
    | {print('')}
    ;


term : <assoc=left> fact restpunkt
    ;

restpunkt : MUL {print('*')} fact restpunkt
    | DIV {print('/')} fact restpunkt
    | {print('')}
    ;

fact :
    Number
    | <assoc=left> {print('(')} expr {print(')')}
    ;

//Klammerung ohne rekursion nicht machbar/hinbekommen

Number : '0' {print('0')}
    | '1' {print('1')}
    | '2' {print('2')}
    | '3' {print('3')}
    | '4' {print('4')}
    | '5' {print('5')}
    | '6' {print('6')}
    | '7' {print('7')}
    | '8' {print('8')}
    | '9' {print('9')}
    ;

MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;

N : '\n';

WS :   (' ' | '\t' | '\r') -> skip;*/
