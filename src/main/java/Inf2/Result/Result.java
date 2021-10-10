package Inf2.Result;


import Inf2.function.Function;
import inout.Effect;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Supplier;


public abstract class Result<T> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static Result empty = new Empty();

    private Result() {
    }

    public abstract T getOrElse(final T defaultValue);

    public abstract T getOrElse(final Supplier<T> defaultValue);

    public abstract <U> Result<U> map(Function<T, U> f) throws IOException;

    public abstract <U> Result<U> flatMap(Function<T, Result<U>> f) throws IOException;

    public abstract Result<T> mapFailure(String s);

    public abstract Result<T> mapFailure(String s, Exception e);

    public abstract Result<T> mapFailure(Exception e);

    public abstract Result<T> failIfEmpty(String message);

    public abstract boolean equals(Object o);

    public abstract void forEachOrThrow(Effect<T> c) throws IOException;

    public abstract Result<String> forEachOrFail(Effect<T> c) throws IOException;

    public abstract void forEach(Effect<T> ef) throws IOException;

    public Result<T> orElse(Supplier<Result<T>> defaultValue) throws IOException {
        return map(x -> this).getOrElse(defaultValue);
    }

    public Result<T> filter(Function<T, Boolean> p) throws IOException {
        return flatMap(x -> p.apply(x)
                ? this
                : failure("Condition not matched"));
    }

    public Result<T> filter(Function<T, Boolean> p, String message) throws IOException {
        return flatMap(x -> p.apply(x)
                ? this
                : failure(message));
    }

    public boolean exists(Function<T, Boolean> p) throws IOException {
        return map(p).getOrElse(false);
    }

    private static class Empty<T> extends Result<T> {

        public Empty() {
            super();
        }

        @Override
        public T getOrElse(final T defaultValue) {
            return defaultValue;
        }

        @Override
        public <U> Result<U> map(Function<T, U> f) {
            return empty();
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return empty();
        }

        @Override
        public Result<T> mapFailure(String s) {
            return this;
        }

        @Override
        public Result<T> mapFailure(String s, Exception e) {
            return this;
        }

        @Override
        public Result<T> mapFailure(Exception e) {
            return this;
        }

        @Override
        public Result<T> failIfEmpty(String message) {
            return failure(message);
        }

        @Override
        public String toString() {
            return "Empty()";
        }

        @Override
        public T getOrElse(Supplier<T> defaultValue) {
            return defaultValue.get();
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

        @Override
        public void forEachOrThrow(Effect<T> c) {
            //Do nothing
        }

        @Override
        public Result<String> forEachOrFail(Effect<T> c) {
            return empty();
        }

        public void forEach(Effect<T> ef) {
            // Do nothing }
        }

    }

    private static class Failure<T> extends Empty<T> {

        private final RuntimeException exception;

        private Failure(String message) {
            super();
            this.exception = new IllegalStateException(message);
        }

        private Failure(RuntimeException e) {
            super();
            this.exception = e;
        }

        private Failure(Exception e) {
            super();
            this.exception = new IllegalStateException(e.getMessage(), e);
        }

        @Override
        public String toString() {
            return String.format("Failure(%s)", exception.getMessage());
        }

        @Override
        public <U> Result<U> map(Function<T, U> f) {
            return failure(exception);
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) {
            return failure(exception);
        }

        @Override
        public Result<T> mapFailure(String s) {
            return failure(new IllegalStateException(s, exception));
        }

        @Override
        public Result<T> mapFailure(String s, Exception e) {
            return failure(new IllegalStateException(s, e));
        }

        @Override
        public Result<T> mapFailure(Exception e) {
            return failure(e);
        }

        @Override
        public Result<T> failIfEmpty(String message) {
            return failure(message);
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }

        public void forEachOrThrow(Effect<T> c) {
            throw this.exception;
        }

        public Result<String> forEachOrFail(Effect<T> c) {
            try {
                return success(exception.getMessage());
            } catch (Exception e) {
                System.err.println(e);
                return empty();
            }
        }

        public Result<RuntimeException> forEachOrException(Effect<T> c) {
            return success(exception);
        }


    }

    private static class Success<T> extends Result<T> {

        private final T value;

        private Success(T val) {
            super();
            this.value = val;
        }

        @Override
        public String toString() {
            return String.format("Success(%s)", value.toString());
        }

        @Override
        public T getOrElse(T defaultValue) {
            return value;
        }

        @Override
        public T getOrElse(Supplier<T> defaultValue) {
            return value;
        }

        @Override
        public <U> Result<U> map(Function<T, U> f) throws IOException {
            return success(f.apply(value));
        }

        @Override
        public <U> Result<U> flatMap(Function<T, Result<U>> f) throws IOException {
            return f.apply(value);
        }

        @Override
        public Result<T> mapFailure(String s) {
            return this;
        }

        @Override
        public Result<T> mapFailure(String s, Exception e) {
            return this;
        }

        @Override
        public Result<T> mapFailure(Exception e) {
            return this;
        }

        @Override
        public Result<T> failIfEmpty(String message) {
            return this;
        }

        @Override
        public boolean equals(Object o) {
            return (this == o || o instanceof Success)
                    && this.value.equals(((Success<?>) o).value);
        }

        @Override
        public void forEachOrThrow(Effect<T> e) throws IOException {
            e.apply(this.value);
        }

        @Override
        public Result<String> forEachOrFail(Effect<T> e) throws IOException {
            try {
                e.apply(this.value);
                return empty();
            } catch (Exception er) {
                System.err.println(er);
                return failure(er);
            }
        }

        public void forEach(Effect<T> ef) {
            try {
                ef.apply(value);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    public static <T> Result<T> failure(String message) {
        return new Failure<>(message);
    }

    public static <T> Result<T> failure(Exception e) {
        return new Failure<>(e);
    }

    public static <T> Result<T> failure(RuntimeException e) {
        return new Failure<>(e);
    }

    public static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> empty() {
        return empty;
    }

    public static <T> Result<T> of(T value) {
        return value != null
                ? success(value)
                : empty();
    }

    public static <T> Result<T> of(T value, String message) {
        return value != null
                ? success(value)
                : failure(message);
    }

    public static <T> Result<T> of(Function<T, Boolean> predicate, T value) {
        try {
            return predicate.apply(value)
                    ? success(value)
                    : empty();
        } catch (Exception e) {
            String errMessage =
                    String.format("Exception while evaluating predicate: %s", value);
            return Result.failure(new IllegalStateException(errMessage, e));
        }
    }

    public static <T> Result<T> of(Function<T, Boolean> predicate,
                                   T value, String message) {
        try {
            return predicate.apply(value)
                    ? Result.success(value)
                    : Result.failure(String.format(message, value));
        } catch (Exception e) {
            String errMessage =
                    String.format("Exception while evaluating predicate: %s",
                            String.format(message, value));
            return Result.failure(new IllegalStateException(errMessage, e));
        }
    }

}