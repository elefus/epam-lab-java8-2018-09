package lambda.part4.example;

import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Predicate;

@Value
public class StageFilter<T> implements Iterable<T> {

    Iterator<T> source;
    Predicate<? super T> condition;

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private T next;

            @Override
            public boolean hasNext() {
                while (source.hasNext()) {
                    next = source.next();
                    if (condition.test(next)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                return next;
            }
        };
    }
}
