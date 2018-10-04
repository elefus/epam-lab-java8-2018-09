package lambda.part4.example;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
public class StageDistinct<T> implements Iterable<T> {

    private final Set<T> values = new HashSet<>();
    private final Iterator<T> source;

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private T next;

            @Override
            public boolean hasNext() {
                while (source.hasNext()) {
                    next = source.next();
                    if (values.add(next)) {
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
