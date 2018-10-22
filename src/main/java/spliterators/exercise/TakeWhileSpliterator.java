package spliterators.exercise;

import java.util.Spliterators;
import java.util.function.Consumer;

public class TakeWhileSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

    public TakeWhileSpliterator() {
        // TODO implementation
        super(0, 0);
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        // TODO implementation
        throw new UnsupportedOperationException();
    }

    // TODO other methods of spliterator
}
