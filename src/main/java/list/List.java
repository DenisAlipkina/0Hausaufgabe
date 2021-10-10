package list;

import Inf2.Result.Result;
import Inf2.function.Function;
import Inf2.tailcall.TailCall;
import inout.Effect;

import java.io.IOException;

import static Inf2.tailcall.TailCall.ret;
import static Inf2.tailcall.TailCall.sus;


//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//---------------------------------------------------------
/*im package main findest du prints für die methoden*/
//---------------------------------------------------------
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

public abstract class List<A> {
    public abstract A head();
    public abstract List<A> tail();
    public abstract boolean isEmpty();

    public abstract int length();
    public abstract boolean elem(A x);
    public abstract boolean any(Function<A, Boolean> p) throws IOException;
    public abstract boolean all(Function<A, Boolean> p) throws IOException;
    public abstract <B> List<B> map(Function<A, B> f) throws IOException;
    public abstract List<A> filter(Function<A, Boolean> f) throws IOException;
    public abstract A finde(Function<A, Boolean> f) throws IOException;
    public abstract List<A> init();
    public abstract A last();
    public abstract List<A> take(int n);
    public abstract List<A> drop(int n);
    public abstract List<A> takeWhile(Function<A, Boolean> p) throws IOException;
    public abstract List<A> dropWhile(Function<A, Boolean> p) throws IOException;
    public abstract List<A> delete(A x);
    public abstract boolean isEqualTo(List<A> xs);

    public abstract <B> B foldr(Function<A, Function<B,B>> f, B s) throws IOException;
    public abstract <B> B foldl(Function<B, Function<A,B>> f, B s) throws IOException;
    public abstract <A> boolean elemfoldr(A y) throws IOException;
    public abstract <A> boolean elemfoldl(A y) throws IOException;
    public abstract <A> boolean anyfoldr(Function<A, Boolean> p) throws IOException;
    public abstract <A> boolean anyfoldl(Function<A, Boolean> p) throws IOException;
    public abstract <A> boolean allfoldr(Function<A, Boolean> p) throws IOException;
    public abstract <A> boolean allfoldl(Function<A, Boolean> p) throws IOException;
    public abstract <A,B> List<B> mapfoldr(Function<A, B> f, List<A> list) throws IOException;
    public abstract List<A> takeWhilefoldr(Function<A, Boolean> p) throws IOException;
    public abstract String toStringfoldr() throws IOException;
    public abstract A lastfoldl() throws IOException;
    public abstract <A> boolean allviaany(Function<A, Boolean> p) throws IOException;
    public abstract <A> boolean elemviaany(A x) throws IOException;
    public abstract <B> List<B> flatMap(Function<A, List<B>> f) throws IOException;
    public abstract Result<A> headOption();
    public abstract Result<A> find(Function<A,Boolean> p) throws IOException;
    public abstract void forEach(Effect<A> ef);


    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    private List() {
    }




    public static class Nil<A> extends List<A> {
        private Nil() {
        }

        public A head() {
            throw new IllegalStateException("head called en empty list");
        }

        public List<A> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public String toString() {
            return "[Nil]";
        }
/*----------------------Instanzmethoden-Nil---------------------------*/
        public int length() {
            return 0;
        }
        public boolean elem(A x){
            return false;
        }
        public boolean any(Function<A, Boolean> p){
            return false;
        }
        public boolean all(Function<A, Boolean> p){
            return true;
        }
        public <B> List<B> map(Function<A, B> f){
            return list();
        }
        public List<A> filter(Function<A, Boolean> f){
            return list();
        }
        public A finde(Function<A, Boolean> f){ return null; } //oder error
        public List<A> init(){ return list(); }
        public A last(){ return null; } //oder error
        public List<A> take(int n){return list();}
        public List<A> drop(int n){return list();}
        public List<A> takeWhile(Function<A, Boolean> p){return list();}
        public List<A> dropWhile(Function<A, Boolean> p){return list();}
        public List<A> delete(A x){return list();}
        public boolean isEqualTo(List<A> xs){return xs.isEmpty();}

        public <B> B foldr(Function<A, Function<B,B>> f, B s){ return s;}
        public <B> B foldl(Function<B, Function<A,B>> f, B s){ return s;}
        public <A> boolean elemfoldr(A y){return false;}
        public <A> boolean elemfoldl(A y){return false;}
        public <A> boolean anyfoldr(Function<A, Boolean> p){return false;}
        public <A> boolean anyfoldl(Function<A, Boolean> p){return false;}
        public <A> boolean allfoldr(Function<A, Boolean> p){return true;}
        public <A> boolean allfoldl(Function<A, Boolean> p){return true;}
        public <A,B> List<B> mapfoldr(Function<A, B> f, List<A> list) {
            return list();}
        public List<A> takeWhilefoldr(Function<A, Boolean> p){return list();}
        public String toStringfoldr() {return ""; }
        public A lastfoldl(){return null;}
        public <A> boolean allviaany(Function<A, Boolean> p){return true;}
        public <A> boolean elemviaany(A x){return false;}
        public <B> List<B> flatMap(Function<A, List<B>> f){return list();}

        @Override
        public boolean equals(Object o) {
            return o instanceof List.Nil;
        }
        public Result<A> headOption() { return Result.empty(); }

        @Override
        public Result<A> find(Function<A, Boolean> p) {
            return Result.empty();
        }

        @Override
        public void forEach(Effect<A> ef) {
            //Do Nothing
        }



    }

    static class Cons<A> extends List<A> {
        private final A head;
        private final List<A> tail;

        Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public List<A> tail() {
            return tail;
        }

        public boolean isEmpty() {
            return false;
        }

        public String toString() {
            return String.format("[%sNil]", toString(new StringBuilder(), this).eval());
        }

        private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
            return list.isEmpty()
                    ? ret(acc)
                    : sus(() -> toString(acc.append(list.head()).append(", "),
                    list.tail()));
        }

        /*----------------------Instanzmethoden-Cons---------------------------*/
        public int length() {
            return 1 + tail().length();
        }

        public boolean elem(A x) {
            return head.equals(x) || tail.elem(x);
        }

        public boolean any(Function<A, Boolean> p) throws IOException {
            return p.apply(head) || tail.any(p);
        }

        public boolean all(Function<A, Boolean> p) throws IOException {
            return p.apply(head) && tail.all(p);
        }

        public <B> List<B> map(Function<A, B> f) throws IOException {
            return new Cons<>(f.apply(head()), tail().map(f));
        }

        public List<A> filter(Function<A, Boolean> f) throws IOException {
            return f.apply(head) && tail.isEmpty() ? list(head) :
                    f.apply(head) && !tail.isEmpty() ? append(list(head), tail.filter(f)) :
                            append(list(), tail.filter(f));
        }

        public A finde(Function<A, Boolean> f) throws IOException {
            return f.apply(head) ? head : tail.finde(f);
        }

        public List<A> init() {
            return !tail.isEmpty() ? append(list(head), tail.init()) : list();

        }

        public A last() {
            return tail.isEmpty() ? head : tail.last();

        }

        public List<A> take(int n) {
            return n == 0 ? list() : append(list(head), tail.take(n - 1));
        }

        public List<A> drop(int n) {
            return n == 1 ? tail : tail.drop(n - 1);
        }

        public List<A> takeWhile(Function<A, Boolean> p) throws IOException {
            return p.apply(head) ? append(list(head), tail.takeWhile(p)) : list();
        }

        public List<A> dropWhile(Function<A, Boolean> p) throws IOException {
            return p.apply(head) ? append(list(), tail.dropWhile(p)) :
                    append(list(head), tail);
        }

        public List<A> delete(A x) {
            return head.equals(x) ? tail : append(list(head), tail.delete(x));
        }

        public boolean isEqualTo(List<A> xs) {
            return head.equals(xs.head()) && tail.isEqualTo(xs.tail());
        }

        public <B> B foldr(Function<A, Function<B, B>> f, B s) throws IOException {
            return this.isEmpty() ? s : f.apply(this.head()).apply(foldr(f, s, this.tail()));
        }

        public <B> B foldl(Function<B, Function<A, B>> f, B s) throws IOException {
            return this.isEmpty() ? s : foldl(f, f.apply(s).apply(this.head()), this.tail());
        }

        public <A> boolean elemfoldr(A y) throws IOException {
            return foldr(x -> z -> (x.equals(y)) || z, false, this);
        }

        public <A> boolean elemfoldl(A y) throws IOException {
            return foldl(z -> x -> (x.equals(y)) || z, false, this);
        }

        public <A> boolean anyfoldr(Function<A, Boolean> p) throws IOException {
            return foldr((x -> y -> p.apply((A) x) || y), false, this);
        }

        public <A> boolean anyfoldl(Function<A, Boolean> p) throws IOException {
            return foldl((y -> x -> p.apply((A) x) || y), false, this);
        }

        public <A> boolean allfoldr(Function<A, Boolean> p) throws IOException {
            return foldr((x -> y -> p.apply((A) x) ? y : false), true, this);
        }

        public <A> boolean allfoldl(Function<A, Boolean> p) throws IOException {
            return foldl((y -> x -> p.apply((A) x) ? y : false), true, this);
        }

        public <A, B> List<B> mapfoldr(Function<A, B> f, List<A> list) throws IOException {
            return foldr(x -> xs -> xs.cons(f.apply(x)), list(), list);
        }

        public List<A> takeWhilefoldr(Function<A, Boolean> p) throws IOException {
            return p.apply(head) ? append(list(head), tail.takeWhile(p)) : list();
        }

        public String toStringfoldr() throws IOException {
            return this.foldr(x -> s -> x + ", " + s, "");
        }

        public A lastfoldl() throws IOException {
            return foldl((x -> y -> list(x).isEmpty() ? this.head() : y), null, this);
        }

        public <A> boolean allviaany(Function<A, Boolean> p) throws IOException {
            return !this.anyfoldl((x -> !p.apply((A) x)));
        }

        public <A> boolean elemviaany(A x) throws IOException {
            return this.anyfoldl(y -> y.equals(x));
        }

        public <B> List<B> flatMap(Function<A, List<B>> f) throws IOException {
            return foldr((h -> t -> append(f.apply(h), t)), list(), this); }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof List.Cons)) return false;

            List<A> list = (Cons<A>) o;

            return (head().equals(list.head()) && tail.equals(list.tail()));
        }
        public Result<A> headOption() { return Result.success(head); }

        @Override
        public Result<A> find(Function<A, Boolean> p) throws IOException {

            return this.finde(p) != null ? Result.success(this.finde(p)) : Result.empty();
        }
        public void forEach(Effect<A> ef) { forEach(this, ef).eval(); }

        private static <A> TailCall<List<A>> forEach(List<A> list, Effect<A> ef) { return list.isEmpty() ? TailCall.ret(list) : TailCall.sus(() -> {
            try {
                ef.apply(list.head());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return forEach(list.tail(), ef); }); }


    }

//------------------------Cons endet hier-------------------------------------------------------

    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> List<A> list(A... a) {
        List<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Cons<>(a[i], n);
        }
        return n;
    }

    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    public static <A> List<A> cons(A a, List<A> list) {
        return new Cons<A>(a, list);
    }

    public static <A> List<A> setHead(List<A> list, A h) {
        if (list.isEmpty()) {
            throw new IllegalStateException("setHead called on an empty list");
        } else {
            return new Cons<>(h, list.tail());
        }
    }


    //Methoden für die Listen -------------1.2--------------------------------

    public static Integer sum(List<Integer> list) {
        return list.isEmpty() ? 0 : list.head() + sum(list.tail());
    }

    public static Double prod(List<Double> list) {
        return list.isEmpty() ? 1 : list.head() * prod(list.tail());
    }

    public static <A> List<A> append(List<A> xs, List<A> ys) {
        return xs.isEmpty() ? ys : new Cons<>(xs.head(), append(xs.tail(), ys));
    }
    public static <A> List<A> concat(List<List<A>> list){
        List<A> empty = list();
        return list.isEmpty() ? empty : list.append(list.head(), concat(list.tail()));
    }

    public static <A> List<A> reverse(List<A> xs) {
        return xs.isEmpty() ? list() : append(reverse(xs.tail()), list(xs.head()));
    }
    public static boolean and(List<Boolean> list){
        return list.isEmpty() || (list.head() && and(list.tail()));
    }
    public static boolean or(List<Boolean> list){
        return !list.isEmpty() && (list.head() || or(list.tail()));
    }

    public static <A extends Comparable<A>> A minimum(List<A> list){
        if(list.isEmpty())
            throw new IllegalStateException("minimum called on an empty list");
        return list.length() == 1 ? list.head() :
                list.head().compareTo(list.tail().head()) > 0 ? minimum(list.tail()) :
                        minimum(append(list(list.head()), list.tail().tail()));

    }
    public static <A extends Comparable<A>> A maximum(List<A> list){
        if(list.isEmpty())
            throw new IllegalStateException("maximum called on an empty list");

        return list.length() == 1 ? list.head() :
                list.head().compareTo(list.tail().head()) > 0 ?
                        maximum(append(list(list.head()), list.tail().tail())) :
                        maximum(list.tail());
    }


    /*foldr -----------1.3--------------------------------------------------------------------*/
    public static <A, B> B foldr(Function<A, Function<B, B>> f, B s, List<A> list) throws IOException {
        return list.isEmpty() ? s : f.apply(list.head()).apply(foldr(f, s, list.tail()));
    }
    public static <A, B> B foldl(Function<B, Function<A, B>> f, B s, List<A> xs) throws IOException {
        return xs.isEmpty() ? s : foldl(f, f.apply(s).apply(xs.head()), xs.tail());
    }





   /* public static Integer sumfoldr(List<Integer> xs) {return foldr(x -> y -> x + y, 0, xs);}
    public static Integer sumfoldl(List<Integer> xs) {return foldl(x -> y -> x + y, 0, xs);}
    public static Double prodfoldr(List<Double> xs) {return foldr(x -> y -> x * y, 1.0, xs);}
    public static Double prodfoldl(List<Double> xs) {return foldl(x -> y -> x * y, 1.0, xs);}
    public static <A> Integer lengthfoldr(List<A> xs) {return foldr(x -> n -> n + 1, 0, xs);}
    public static <A> Integer lengthfoldl(List<A> xs) {return foldl(n -> x -> n + 1, 0, xs);}
    public static boolean andfoldr(List<Boolean> list){return foldr(x -> y -> x && y, true, list);}
    public static boolean andfoldl(List<Boolean> list){return foldl(x -> y -> x && y, true, list);}
    public static boolean orfoldr(List<Boolean> list){return foldr(x -> y -> x || y, false, list);}
    public static boolean orfoldl(List<Boolean> list){return foldl(x -> y -> x || y, false, list);}
    public static <A> List<A> appendfoldr(List<A> xs, List<A> ys) {
        return foldr(x -> l -> l.cons(x), ys, xs);}
    public static <A> List<A> concatfoldr(List<List<A>> list){
        return list.isEmpty() ? list() : list.append(list.head(), concat(list.tail()));}
    public static <A> List<A> filterfoldr(Function<A, Boolean> p, List<A> list) {
        return foldr(x -> xs -> p.apply(x) ? xs.cons(x) : xs, list(), list); }
    public static <A> List<A> reversefoldr(List<A> list) {
        return foldr(x -> xs -> xs.append(xs, list(x)), list(), list); }
    public static <A> List<A> reversefoldl(List<A> list) {
        return foldl(xs -> xs::cons, list(), list); }*/

    public static List<Integer> range(int start, int end){

        return start <= end ? new Cons<>(start, range(start +1, end)) : list();
    }

    //-------------------------------------------words
    public static List<String> words(String s){
        return !s.isEmpty() ? List.list(s.split("\\s+")) : list();
    }
    public static List<Object> word(String s){
        return !s.isEmpty() ? List.list(s.split("\\s+")) : list();
    }
    //-------------------Euler5----------------------------
    public static int ggT(int a, int b) {
        if (a < 0 || b < 0) return 0;
        if (a == b || b == 0) return a;
        else return ggT(b, a % b);
    }
    public static int kgV(int a, int b) {
        if (a < 0 || b < 0) return 0;
        if (a == b || b == 1) return a;
        if (a == 1) return b;
        else return (a * (b / (ggT(a,b))));
    }
/*    public static Integer euler1(List<Integer> list) {return sum(list.filter(x -> x%5 == 0 || x%3 == 0));}
    public static Integer euler5(List<Integer> list){
        return foldl((x -> y -> kgV(x, y)), 1, list);
    }*/



}