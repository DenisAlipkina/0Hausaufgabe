package inout;

import java.io.IOException;

public interface Effect<T> {
    void apply(T t) throws IOException;
}