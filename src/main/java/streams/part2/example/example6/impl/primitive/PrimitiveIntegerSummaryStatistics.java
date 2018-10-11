package streams.part2.example.example6.impl.primitive;

import lombok.Getter;
import streams.part2.example.example6.IntegerSummaryStatistics;

import java.util.concurrent.atomic.LongAdder;

@Getter
public class PrimitiveIntegerSummaryStatistics implements IntegerSummaryStatistics {

    private long count;
    private long sum;
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;

    @Override
    public void accept(Integer value) {
        ++count;
        sum += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public PrimitiveIntegerSummaryStatistics merge(IntegerSummaryStatistics other) {
        count += other.getCount();
        sum += other.getSum();
        min = Math.min(min, other.getMin());
        max = Math.max(max, other.getMax());
        return this;
    }

    @Override
    public double getAverage() {
        return count == 0 ? 0 : (double)sum / count;
    }
}
