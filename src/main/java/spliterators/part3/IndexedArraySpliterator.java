package spliterators.part3;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class IndexedArraySpliterator<T> extends Spliterators.AbstractSpliterator<IndexedValue<T>> {

    private final T[] source;
    private AtomicInteger index = new AtomicInteger();

    public IndexedArraySpliterator(T[] data) {
        super(Objects.requireNonNull(data).length, SIZED | IMMUTABLE | ORDERED);
        this.source = data;
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedValue<T>> action) {
        if (index.get() == source.length) {
            return false;
        }
        action.accept(new IndexedValue<>(index.get(), source[index.getAndIncrement()]));
        return index.get() != source.length;
    }

    @Override
    public Spliterator<IndexedValue<T>> trySplit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long estimateSize() {
        return getExactSizeIfKnown();
    }

    @Override
    public long getExactSizeIfKnown() {
        return source.length - index.get();
    }
}
