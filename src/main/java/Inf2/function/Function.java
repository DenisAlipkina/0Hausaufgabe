package Inf2.function;

import java.io.IOException;

public interface Function<T, U> {
    U apply(T arg) throws IOException;

    static Function<Integer, Integer> compose(Function<Integer, Integer> f1,
                                              Function<Integer, Integer> f2) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) throws IOException {
                return f1.apply(f2.apply(arg));
            }
        };
    }

    static Function<Integer, Integer> composeLambda1(Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return arg -> f1.apply(f2.apply(arg));
    }

    static <T, U, V> Function<V, U> composeLambda2(Function<T, U> f1, Function<V, T> f2) {
        return arg -> f1.apply(f2.apply(arg));
    }

    Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

    Function<Integer, Integer> factorial = n ->
    {
        if (n <= 1) return n;
        return n * Function.factorial.apply(n - 1);
    };

    static Function<Boolean, Boolean> not(){
        return arg -> !arg;
    }


    public static void main(String[] args) throws IOException {
        System.out.println(add.apply(5).apply(7));
        Boolean s = false;
        System.out.println(add.apply(5).apply(7));
    }
    /*
Was ist eine Funktion?
Eine Funktion ist ein mathematisches Obkekt, welche für jedes Element der Domäne einen eindeutigen Wert aus der Codomäne
zuordnet.

Folgende Konzepte sollen Sie verstehen und erklären können:

Mengentheoretische Definition einer Funktion:
Funktion ist eine rechtseindeutige Relation.

Umkehrfunktionen (Inverse Functions):
Funktion, die jedem Element der Zielmenge sein eindeutig bestimmtes Urbildelement zuweist. Z.B. f(x) = y. Dann wäre
die inverse Funktion f**-1(y) = x.

Partielle Funktionen (Partial Functions):
Bezeichnung für eine Funktion, die evtl. für manche Werte aus ihrem Definitionsbereich den speziellen Funktionswert
"undefiniert" hat.

Verkettung von Funktionen (Function Composition):
Verkettung von Funktion f und g würde so aussehen: f(g(x)) = (f * g) (x). Funktion f wäre die äußere und g die innere
Funktion. Bei einem Funktionsargument würde die Komposition den selben Funktionswert erreichen wie die
Hintereinanderkettung von g(x) und f(x).

Funktionen in mehreren Argumenten (Functions of Several Arguments):
Sind "Wenn-Funktionen". Wenn dies dann tue das und wenn nicht tue das. Ähnlich wie If-Anweisungen oder Schaltnetze.

Curryfizierung (Currying):
(x, y) -> x + y wäre nicht curryfizierte Lambda-Funktion. x -> y -> x + y wäre eine curryfizierte Lambda-Funktion.
Umwandlung einer Funktion mit mehreren Argumenten in eine Sequenz von Funktionen mit jeweils einem Argument.

Partielle Anwendung von Funktionen (Partially Applied Functions):
Zwei Funktionen mit vertauschten Variablen. Zum Beispiel: Funktion(lohn, rate) welche einem Lohn einen prozentualen
Anteil raufaddiert. Nun kann man dies Funktion in (lohn) und Funktion(rate) aufteilen. Die eine Funktion rechnet auf
einen FESTEN Lohn einen VARIABLEN Prozentsatz rauf und die andere würde auf einen VARIABLEN Lohn einen FESTEN
Prozentsatz raufaddieren.*/
}
