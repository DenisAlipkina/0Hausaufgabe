### Aufgabe 2
Gegeben sei die folgende durch Syntaxgraphen beschriebene Grammatik für die Sprache Exprit.
           
1. Von welchem Chomsky-Typ ist die Grammatik?
2. Ist die Grammatik eindeutig?
3. Geben Sie für jede Regel an, ob sie links- oder rechtsrekursiv ist.
4. Bestimmen Sie die Assoziativitäten und Prioritäten aller Operatoren!
5. Geben Sie den Syntaxbaum für folgende Eingabe an: 10 \* 2 / 2 + 4
6. Entwickeln Sie mit ANTLR einen Syntaxerkenner für die Sprache. Der Syntaxerkenner soll die Werte der arithmetischen Ausdrücke nicht berechnen, sondern nur deren syntaktische Korrektheit prüfen.
7. Schreiben Sie einen **Parametrized** JUnit-Test,
   der prüft, ob der Syntaxerkenner korrekte Syntaxbäume liefert!
8. Erstellen Sie eine CI-Pipeline, die den Test ausführt.


#####Antworten
1. Kontextfrei, weil bei der Produktions regel links nur nicht Terminale sein
2. Nein, 2+2 könnte man mit der Regel expr und fact erstellen
3. 
|Regel|Rekursion|
|---|---|
|prog ->|keine|
|stat ->|keine|
|expr ->|indirekt|
|term ->|indirekt|
|fact ->|rechts|
4. 
|Operator|Assoziativität|Priorität|
|---|---|---|
|+|links|2|
|-|links|2|
|*|links|3|
|/|links|3|
|(,)|keine bzw. links|4|
|int|keine bzw. links|4|
|fact +|rechts|4|
|fact -|rechts|4|
|n|keine bzw. links|1|
5. 
```
                prog
                 |
                stat
               /     \  
              expr    n
          /     |   \  
        term    +    term
    / /  |  \ \         \
 fact * fact \ fact     fact
   |     |      |        |
  int   int    int      int

   int * int / int + int n
```