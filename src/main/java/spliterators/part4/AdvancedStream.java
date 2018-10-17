package spliterators.part4;

import spliterators.part3.IndexedValue;

import java.util.function.Predicate;
import java.util.stream.Stream;

// a = [A B C D E F G]
// b = [1 2 3 4 5 6 7]
public interface AdvancedStream<T> extends Stream<T> {

    // b.dropWhile(val < 3) = [3 4 5 6 7]
    AdvancedStream<T> dropWhile(Predicate<T> condition);

    // b.takeWhile(val < 3) = [1 2]
    AdvancedStream<T> takeWhile(Predicate<T> condition);

    // a.zip(b) = [{A, 1}, {B, 2}, {C, 3}...]
    <R> AdvancedStream<Pair<T, R>> zip(AdvancedStream<R> other);

    // a.zipWithIndex() = [{0, A}, {1, B}, {2, C}...]
    AdvancedStream<IndexedValue<T>> zipWithIndex();

    static <T> AdvancedStream<T> of(Stream<T> source) {
        return new AdvancedStreamImpl<>(source);
    }
}
