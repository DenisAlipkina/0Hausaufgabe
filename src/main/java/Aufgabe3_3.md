### Aufgabe 2
Gegeben sei folgende Grammatik für die Ausdruckssprache Expr:
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
1. Von welchem Chomsky-Typ ist die Grammatik?
2. Ist die Grammatik eindeutig?
3. Geben Sie für jede Regel an, ob sie links- oder rechtsrekursiv ist.
4. Bestimmen Sie die Assoziativitäten und Prioritäten aller Operatoren!
5. Geben Sie den Syntaxbaum für folgende Eingabe an: 10 \* 2 / 2 + 4
6. Entwickeln Sie mit ANTLR einen Syntaxerkenner für die Sprache. Der Syntaxerkenner soll wiederum nur die syntaktische Korrektheit der arithmetischen Ausdrücke prüfen.
7. Vereinbaren Sie die Assoziativitäten und Prioritäten der Operatoren wie folgt:
   1. Die Operatoren +, -, \*, / sollen linksassoziativ sein.
   2. Die Klammerung soll die höchste Priorität haben. Dann folgt die Punktrechnung (\*, / ) und zum Schluss die Strichrechung (+, - ).
8. Schreiben Sie einen **Parametrized** JUnit-Test,
   der prüft, ob der Syntaxerkenner korrekte Syntaxbäume liefert!
9. Erstellen Sie eine CI-Pipeline, die den Test ausführt.



#####Antworten
1. Kontextfrei, weil bei der Produktions regel links nur nicht Terminale sein
2. Nein, 2+2+2 könnte man links und rechtsrekurssiv lösen
3. 
|Regel|Rekursion|
|---|---|
|prog ->|keine|
|stat ->|keine|
|expr ->|direkt rekursiv|

4. 
|Operator|Assoziativität|Priorität|
|---|---|---|
|+|nicht bestimmbar|2|
|-|nicht bestimmbar|2|
|*|nicht bestimmbar|2|
|/|nicht bestimmbar|2|
|(,)|keine bzw. links|2|
|int|keine bzw. links|2|
|n|keine bzw. links|1|
5. Linksrekursive Variante
<pre>
             prog
              |
             stat
             /  \  
            expr n
          /  |  \  
        expr + expr
         |       \
        expr    int
      /  |  \
    expr / expr
  /  |  \    |
expr * expr int
 |      |
int    int
</pre>
6. 
7. 
<pre>
    N = { prog, stat, expr }

    T = { int, + ,- ,* ,/ ,( ,) ,n }

    P = { prog → stat<b>+</b>
          stat → expr n | n
          expr → expr(+|-)term | term
          term → term(*|/)fact | fact
          fact → (expr) | int      }
</pre>


Feedback:
statt Regel ist beides -> Regel ist direkt rekursiv