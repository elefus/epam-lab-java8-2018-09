package lambda.part4.example;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class StageLimit<T> implements Iterable<T> {

    Iterator<T> source;
    long maxSize;

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return maxSize != 0 && source.hasNext();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    --maxSize;
                    return next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
