package spliterators.part4;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DropWhileSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

    private final Spliterator<T> source;
    private final Predicate<? super T> predicate;
    private boolean dropped;

    protected DropWhileSpliterator(Spliterator<T> source, Predicate<? super T> predicate) {
        super(source.estimateSize(), source.characteristics() & ~(SIZED | SUBSIZED));
        this.predicate = predicate;
        this.source = source;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (dropped) {
            return source.tryAdvance(action);
        }
        while (!dropped && source.tryAdvance(value -> {
            if (!predicate.test(value)) {
                action.accept(value);
                dropped = true;
            }
        }));
        return dropped;
    }

    @Override
    public Spliterator<T> trySplit() {
        throw new UnsupportedOperationException();
    }
}
