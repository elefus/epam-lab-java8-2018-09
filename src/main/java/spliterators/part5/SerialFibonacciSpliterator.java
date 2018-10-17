package spliterators.part5;

import java.util.Comparator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SerialFibonacciSpliterator extends Spliterators.AbstractLongSpliterator {

    private static final int BOUND_INDEX = 92;
    private long prev = 0;
    private long curr = 1;
    private int index = 0;

    public SerialFibonacciSpliterator() {
        super(BOUND_INDEX, SIZED | ORDERED | IMMUTABLE | NONNULL | SORTED);
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        if (index == BOUND_INDEX) {
            return false;
        }
        if (index != 0) {
            long next = prev + curr;
            prev = curr;
            curr = next;
        }
        action.accept(curr);
        return ++index != BOUND_INDEX;
    }

    @Override
    public void forEachRemaining(LongConsumer action) {
        while (index != BOUND_INDEX) {
            if (index != 0) {
                long next = prev + curr;
                prev = curr;
                curr = next;
            }
            action.accept(curr);
            ++index;
        }
    }

    @Override
    public Comparator<? super Long> getComparator() {
        return null;
    }

    @Override
    public OfLong trySplit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long estimateSize() {
        return getExactSizeIfKnown();
    }

    @Override
    public long getExactSizeIfKnown() {
        return BOUND_INDEX - index;
    }
}
