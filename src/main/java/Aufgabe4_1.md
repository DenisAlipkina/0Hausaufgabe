# Aufgabe 1:  Syntaxgesteuerte Definition, Übersetzungsschema
##### 1. Entwerfen Sie eine syntaxgesteuerte Definition für einen Kalkulator, der arithmetische Ausdrücke auswertet, die gemäß der unten gegebenen (mehrdeutigen) Grammatik aufgebaut sind. Regeln Sie die Prioritäten und Assoziativitäten analog zum vorherigen Praktikum. 
<pre>
    N = { prog, stat, expr }

    T = { int, + ,- ,* ,/ ,( ,) ,n }

    P = { prog → stat<b>+</b>
          stat → expr n | n
          expr → int
          expr → expr+expr
          expr → expr-expr
          expr → expr*expr
          expr → expr/expr
          expr → (expr)      }
</pre>

|Produktion|Symantische Regel|
|---|---|
|prog → stat+|prog.v = stat.v|
|stat → expr n or n|stat.v = expr.v|
|expr → int<br/>expr → expr(\*/+-)expr<br/>expr → (expr)|expr.v = int.v<br/>expr.v = expr1.v(\*/+-)expr2.v<br/>expr.v = expr1.v|



##### 2. Geben Sie für jedes Attribut an, ob es synthetisiert oder vererbt ist.
Alle sind synthetisiert, da alle Attribute von unten nach oben "gereicht" werden.
In anderen Worten, wird der Baum von Unten nach Oben berechnet. (Bottom-Up)



##### 3. Ist Ihre syntaxgesteuerte Definition S- oder L-attributiert?
Aus Aufgabe 2 folgt: S-Attributiert.
Auch L-attributiert, da bei jeder Produktion die rechte Seite der Produktion nur von sich aus abhängt
und von den vererbten Attributen von der linken Seite der Produktion.



##### 4. Geben Sie für die Ausdrücke 9–5+2 und 9–5\*2 annotierte Parse-Bäume an.
<pre>
Rechtsassoziativ (erst + dann -, also von rechts nach links Rechnung)

                 9-5+2

                 prog.v = 6
                      |
                 stat.v = 6
                      |
                 expr.v = 6
                  /    |    \
            expr.v = 4 + expr.v = 2
          /    |    \           \
    expr.v = 9 - expr.v = 5    int.v = 2
      |             |
    int.v = 9    int.v = 5    



     
     
     
                 9-5*2

                 prog.v = -1
                      |
                 stat.v = -1
                      |
                 expr.v = -1
                  /    |    \
            expr.v = 9 - expr.v = 10
          /               /   |    \
    int.v = 9     expr.v = 5 * expr.v = 2
                      |            |
                  int.v = 5    int.v = 2

       


</pre>



##### 5. Entwickeln Sie ein zu 1) äquivalentes Übersetzungsschema und wiederholen sie 4).

|Produktion| Symantische Regel          |Produktion mit Aktion|
|---|----------------------------|---|
|prog → stat+| prog.v = stat.v            |{prog.v = stat.v}|
|stat → expr n or n| stat.v = expr.v            |{stat.v = expr.v}|
|expr → int| expr.v = int.v             |{expr.v = int.v}|
|expr → expr * expr| expr.v = expr1.v * expr2.v |{expr.v = expr1.v * expr2.v}|
|expr → expr / expr| expr.v = expr1.v / expr2.v  |{expr.v = expr1.v / expr2.v}|
|expr → expr + expr| expr.v = expr1.v + expr2.v  |{expr.v = expr1.v + expr2.v}|
|expr → expr - expr| expr.v = expr1.v - expr2.v  |{expr.v = expr1.v - expr2.v}|
|expr → (expr)| expr.v = expr.v            |{expr.v = expr.v}|

|Produktion|Symantische Regel|Produktion mit Aktion|
|---|---|---|
|prog → stat+|prog.v = stat.v|/|
|stat → expr n|stat.v = expr.v|stat → expr {print('\n')}|
|stat → n|/|stat → {print('\n')}|
|expr → int|expr.v = int.v|/|
|expr → expr * expr|expr.v = expr1.v * expr2.v|expr → expr * {print('*')} expr|
|expr → expr / expr|expr.v = expr1.v / expr2.v|expr → expr / {print('/')} expr|
|expr → expr + expr|expr.v = expr1.v + expr2.v|expr → expr + {print('+')} expr|
|expr → expr - expr|expr.v = expr1.v - expr2.v|expr → expr - {print('-')} expr|
|expr → (expr)|expr.v = expr.v|expr → {print('(')} expr {print(')')} |
|int → 0|/|{print('0')}|
|int → 1|/|{print('1')}|
|...|...|...|
|int → 9|/|{print('9')}|

<pre>
                 9-5*2

                             prog
                              |
                             stat
                       /               \
                     expr               n {print('\n')}
                /  /      \     \
              expr - {print('-')} expr
               |                  /  /  \ \
              int             expr * {print('*')} expr
             /   \             |                    | 
            9 {print('9')}    int                  int
                             /   \                /   \
                            5 {print('5')}       2 {print('2')}
    
    
    
                 9-5+2
                 
                             prog
                              |
                             stat
                       /               \
                     expr               n {print('\n')}
                /  /      \     \
              expr + {print('+')} expr
          /  /  \        \           \
      expr - {print('-')} expr       int
       |                    |       /   \
      int                  int     2 {print('2')}
     /   \                /   \
   9 {print('9')}        5 {print('5')}     

</pre>
<pre>
               prog.v {prog.v = stat.v}
                 |
               stat.v {stat.v = expr.v} 
                 |
               expr.v {expr.v = expr1.v}
           /     |     \
         expr.v    +    expr.v {expr.v = expr1.v + expr2.v}
    /     |    \\                                   \
expr.v    -    expr.v {expr.v = expr1.v - expr2.v}   int.v {expr.v = int.v}  
 |                     \                               |
int.v {expr.v = int.v}  int.v {expr.v = int.v}         2
 |                       |
 9                       5

         prog.v {prog.v = stat.v}
          |
        stat.v {stat.v = expr.v}
          |    \
         expr.v {expr.v = expr1.v}
    /     |     \ \
expr.v    -    expr.v {expr.v = expr1.v - expr2.v}
 |  \                 \ \\\                
int.v {expr.v = int.v} expr.v * expr.v {expr.v = expr1.v * expr2.v} 
 |                      /   \              \\
 9                    int.v {expr.v = int.v} int.v {expr.v = int.v}
                        |                      |
                        5                      2
</pre>