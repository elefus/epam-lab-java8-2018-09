package spliterators.part4;

import spliterators.part3.IndexedValue;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class IndexedSpliterator<T> extends Spliterators.AbstractSpliterator<IndexedValue<T>> {

    private static final long THRESHOLD = 10_000;
    private final Spliterator<T> source;
    private long index;
    private long bound;

    public IndexedSpliterator(Spliterator<T> source) {
        this(source, 0, source.estimateSize());
    }

    private IndexedSpliterator(Spliterator<T> source, long from, long bound) {
        super(source.estimateSize(), checkCharacteristics(source));
        this.source = source;
        this.index = from;
        this.bound = bound;
    }

    private static <T> int checkCharacteristics(Spliterator<T> source) {
        if (!source.hasCharacteristics(SUBSIZED)) {
            throw new IllegalArgumentException("Non-subsized spliterator!");
        }
        return source.characteristics();
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedValue<T>> action) {
        if (index == bound) {
            return false;
        }
        source.tryAdvance(value -> action.accept(new IndexedValue<>(index++, value)));
        return true;
    }

    @Override
    public Spliterator<IndexedValue<T>> trySplit() {
        if (estimateSize() < THRESHOLD) {
            return null;
        }
        Spliterator<T> prefix = source.trySplit();
        return prefix == null ? null
                              : new IndexedSpliterator<>(prefix, index, index += prefix.estimateSize());
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedValue<T>> action) {
        source.forEachRemaining(value -> action.accept(new IndexedValue<>(index++, value)));
    }

    @Override
    public boolean hasCharacteristics(int characteristics) {
        return source.hasCharacteristics(characteristics);
    }
}
