package spliterators.part1;

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntArraySpliterator extends Spliterators.AbstractIntSpliterator {

    private static final int THRESHOLD = 100_000;
    private final int[] source;
    private int startInclusive;
    private int endExclusive;

    public IntArraySpliterator(int[] data) {
        this(data, 0, data.length);
    }

    private IntArraySpliterator(int[] data, int startInclusive, int endExclusive) {
        super(data.length, SIZED | IMMUTABLE | NONNULL | ORDERED);
        this.source = data;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (startInclusive == endExclusive) {
            return false;
        }
        action.accept(source[startInclusive]);
        return startInclusive++ != endExclusive;
    }

    @Override
    public long estimateSize() {
        System.out.println("IntArraySpliterator::estimateSize called");
        return getExactSizeIfKnown();
    }

    @Override
    public long getExactSizeIfKnown() {
        System.out.println("IntArraySpliterator::getExactSizeIfKnown called " + (endExclusive - startInclusive));
        return endExclusive - startInclusive;
    }

    @Override
    public OfInt trySplit() {
        System.out.println("TrySplit called");
        if (getExactSizeIfKnown() < THRESHOLD) {
            return null;
        }
        System.out.println("Splitted");
        int mid = startInclusive + (endExclusive - startInclusive) / 2;
        return new IntArraySpliterator(source, startInclusive, startInclusive = mid);
    }

    @Override
    public void forEachRemaining(IntConsumer action) {
        while (startInclusive != endExclusive) {
            action.accept(source[startInclusive++]);
        }
    }
}
