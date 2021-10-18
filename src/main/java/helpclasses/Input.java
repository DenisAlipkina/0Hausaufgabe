package helpclasses;


import fpinjava.common.Result;
import fpinjava.common.Stream;
import fpinjava.common.Tuple;


public interface Input<A> extends AutoCloseable {
    Result<Tuple<A, Input<A>>> read();

    default Stream<A> stream() {
        return Stream.<A,Input<A>>unfold(this, Input::read);
    }
}