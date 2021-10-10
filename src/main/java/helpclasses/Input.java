package helpclasses;

import Inf2.Result.Result;
import Inf2.tuple.Tuple;
import fpjava.Stream;

import java.io.IOException;

public interface Input<A> extends AutoCloseable {
    Result<Tuple<A, Input<A>>> read();

    default Stream<A> stream() throws IOException {
        return Stream.<A,Input<A>>unfold(this, Input::read);
    }
}