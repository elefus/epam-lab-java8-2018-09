package generics.nonrecursive;

import java.util.function.IntFunction;
import java.util.function.Predicate;

public interface Stream<T> extends BaseStream<T> {

    Stream<T> filter(Predicate<T> predicate);
    Stream<T> distinct();
    T[] toArray(IntFunction<T[]> generator);

    Stream<T> sequential();
    Stream<T> parallel();
}
