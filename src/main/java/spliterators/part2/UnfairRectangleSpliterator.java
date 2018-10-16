package spliterators.part2;

import java.util.Arrays;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

import static java.util.Spliterator.IMMUTABLE;
import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterator.SIZED;
import static java.util.Spliterator.SUBSIZED;

/**
 * > 1 4 3
 * 3 4 5
 * > 3 4 5
 * 3 4 5
 * 3 4 5
 *
 */
public class UnfairRectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] source;
    private final int rowLength;
    private int startInclusive;
    private int endExclusive;
    private int indexCurrentElement;

    private UnfairRectangleSpliterator(int[][] data) {
        this(data, 0, data.length);
    }

    private UnfairRectangleSpliterator(int[][] data, int startInclusive, int endExclusive) {
        super(data.length * data[0].length, SIZED | NONNULL | ORDERED | IMMUTABLE);
        rowLength = data[0].length;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.source = data;
    }

    public static UnfairRectangleSpliterator of(int[][] data) {
        Objects.requireNonNull(data);
        if (data.length == 0) {
            throw new IllegalArgumentException();
        }
        int rowLength = data[0].length;
        if (rowLength == 0) {
            throw new IllegalArgumentException();
        }
        if (Arrays.stream(data).map(Objects::requireNonNull).anyMatch(row -> row.length != rowLength)) {
            throw new IllegalArgumentException();
        }
        return new UnfairRectangleSpliterator(data);
    }

    @Override
    public long estimateSize() {
        return getExactSizeIfKnown();
    }

    @Override
    public long getExactSizeIfKnown() {
        return (endExclusive - startInclusive - 1) * rowLength + rowLength - indexCurrentElement;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (startInclusive == endExclusive) {
            return false;
        }

        action.accept(source[startInclusive][indexCurrentElement++]);
        if (indexCurrentElement == rowLength) {
            indexCurrentElement = 0;
            ++startInclusive;
        }
        return startInclusive != endExclusive;
    }

    @Override
    public OfInt trySplit() {
        throw new UnsupportedOperationException();
    }
}
