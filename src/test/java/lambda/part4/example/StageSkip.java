package lambda.part4.example;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class StageSkip<T> implements Iterable<T> {

    Iterator<T> source;
    long numberElementsToSkip;

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                while (numberElementsToSkip > 0 && source.hasNext()) {
                    source.next();
                    --numberElementsToSkip;
                }
                return source.hasNext();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    return source.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
