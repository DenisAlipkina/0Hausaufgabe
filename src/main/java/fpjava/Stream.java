package fpjava;

import Inf2.Result.Result;
import Inf2.function.Function;
import Inf2.tailcall.TailCall;
import Inf2.tuple.Tuple;
import inout.Effect;
import inout.Supplier;
import list.List;

import java.io.IOException;

import static Inf2.tailcall.TailCall.ret;
import static Inf2.tailcall.TailCall.sus;
import static list.List.list;

public abstract class Stream<A> {
    private static Stream EMPTY = new Empty();

    public static <A> Stream<A> empty() {
        return EMPTY;
    }

    public abstract A head();

    public abstract Stream<A> tail();

    public abstract Boolean isEmpty();

    public abstract Result<A> headOption();

    public abstract Stream<A> take(int n);

    public abstract Stream<A> drop(int n);

    public abstract void forEach(Effect<A> ef);

    private Stream() {
    }



    private static class Empty<A> extends Stream<A> {
        @Override
        public Stream<A> tail() {
            throw new IllegalStateException("tail called on empty");
        }

        @Override
        public A head() {
            throw new IllegalStateException("head called on empty");
        }

        @Override
        public Boolean isEmpty() {
            return true;
        }

        @Override
        public Result<A> headOption() {
            return Result.empty();
        }

        @Override
        public Stream<A> take(int n) {
            return this;
        }

        @Override
        public Stream<A> drop(int n) {
            return this;
        }

        public void forEach(Effect<A> ef) { // Do nothing

        }

        private static class Cons<A> extends Stream<A> {
            private final Supplier<A> head;
            private A h;
            private final Supplier<Stream<A>> tail;
            private Stream<A> t;

            private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
                head = h;
                tail = t;
            }

            @Override
            public A head() {
                if (h == null) {
                    h = head.get();
                }
                return h;
            }

            @Override
            public Stream<A> tail() {
                if (t == null) {
                    t = tail.get();
                }
                return t;
            }

            @Override
            public Boolean isEmpty() {
                return false;
            }

            @Override
            public Result<A> headOption() {
                return Result.success(head());
            }

            public void forEach(Effect<A> ef) {
                forEach(this, ef).eval();
            }


            private static <A> TailCall<Stream<A>> forEach(Stream<A> s, Effect<A> ef) {
                return s.isEmpty() ? ret(s) : sus(() -> {
                    try {
                        ef.apply(s.head());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return forEach(s.tail(), ef);
                });
            }

            public Stream<A> take(int n) {
                return n <= 0 ? empty() : cons(head, () -> tail().take(n - 1));
            }

            public Stream<A> drop(int n) {
                return drop(this, n).eval();
            }

            public TailCall<Stream<A>> drop(Stream<A> acc, int n) {
                return n <= 0 ? ret(acc) : sus(() -> drop(acc.tail(), n - 1));
            }


        }

        static <A> Stream<A> cons(Supplier<A> hd, Supplier<Stream<A>> tl) {
            return new Cons<>(hd, tl);
        }

        static <A> Stream<A> cons(Supplier<A> hd, Stream<A> tl) {
            return new Cons<>(hd, () -> tl);
        }

        @SuppressWarnings("unchecked")
        public static <A> Stream<A> empty() {
            return EMPTY;
        }

        public static Stream<Integer> from(int i) {
            return cons(() -> i, () -> from(i + 1));
        }

        public List<A> toList() {
            return List.reverse(toList(this, list()).eval());
        }

        private TailCall<List<A>> toList(Stream<A> s, List<A> acc) {
            return s.isEmpty() ? ret(acc) : sus(() -> toList(s.tail(), List.cons(s.head(), acc)));
        }




    }
    public static <A, S> Stream<A> unfold(S z, Function<S, Result<Tuple<A, S>>> f) throws IOException {
        return f.apply(z).map(x -> Empty.cons(() -> x.fst, () -> {
            try {
                return unfold(x.snd, f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        })).getOrElse(Empty.empty());
    }


}
