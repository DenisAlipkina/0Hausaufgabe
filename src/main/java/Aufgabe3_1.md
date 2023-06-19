### Aufgabe 1
Gegeben sei die aus der Vorlesung bekannte (etwas erweiterte) Grammatik G6:

    N6 = { prog,stat,expr,term,fact }

    T6 = { int, +, -, *, /, (, ), n }

    P6 = { prog → stat | prog stat
           stat → expr n | n
           expr → term | expr+term | expr-term
           term → fact | term*fact | term/fact
           fact → int | (expr)                 }
           
1. Von welchem Chomsky-Typ ist die Grammatik?
2. Ist die Grammatik eindeutig?
3. Geben Sie für jede Regel an, ob sie links- oder rechtsrekursiv ist.
4. Bestimmen Sie die Assoziativitäten und Prioritäten aller Operatoren!
5. Geben Sie den Syntaxbaum für folgende Eingabe an: 10 \* 2 / 2 + 4
6. Entwickeln Sie mit ANTLR einen Syntaxerkenner für die Sprache.
   Der Syntaxerkenner soll die Werte der arithmetischen Ausdrücke nicht berechnen,
   sondern nur deren syntaktische Korrektheit prüfen.
7. Schreiben Sie einen **Parametrized** JUnit-Test,
   der prüft, ob der Syntaxerkenner korrekte Syntaxbäume liefert!
8. Erstellen Sie eine CI-Pipeline, die den Test ausführt.

#####Antworten
1. Kontextfrei, weil bei der Produktions regel links nur nicht Terminale sein
2. Ja, stichprobenartig keine Sätze mit Mehrdeutigkeit gefunden
3. 
|Regel|Rekursion|
|---|---|
|prog ->|direkt links|
|stat ->|keine|
|expr ->|direkt rechts|
|term ->|direkt rechts|
|fact ->|indirekt|
4. 
|Operator|Assoziativität|Priorität|
|---|---|---|
|+|rechts|2|
|-|rechts|2|
|*|rechts|3|
|/|rechts|3|
|(,)|keine bzw. links|4|
|int|keine bzw. links|4|
|n|keine bzw. links|1|
5.
```
             prog
              |
             stat
             /  \  
            expr n
          /  |  \  
        expr + term
         |       \
        term    fact
      /  |  \     |
    term / fact  int
  /  |  \    |
term * fact int
 |      |
fact   int
 |
int

int * int / int + int n
```