package generics.recursive;

import java.util.function.IntFunction;
import java.util.function.Predicate;

public interface Stream<T> extends BaseStream<T, Stream<T>> {

    Stream<T> filter(Predicate<T> predicate);
    Stream<T> distinct();
    T[] toArray(IntFunction<T[]> generator);
}
