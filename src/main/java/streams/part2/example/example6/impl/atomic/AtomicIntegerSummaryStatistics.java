package streams.part2.example.example6.impl.atomic;

import streams.part2.example.example6.IntegerSummaryStatistics;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class AtomicIntegerSummaryStatistics implements IntegerSummaryStatistics {

    private final LongAdder count = new LongAdder();
    private final LongAdder sum = new LongAdder();
    private final AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
    private final AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);

    @Override
    public void accept(Integer value) {
        count.increment();
        sum.add(value);
        min.getAndUpdate(oldValue -> Math.min(oldValue, value));
        max.getAndUpdate(oldValue -> Math.max(oldValue, value));
    }

    @Override
    public IntegerSummaryStatistics merge(IntegerSummaryStatistics other) {
        count.add(other.getCount());
        sum.add(other.getSum());
        min.getAndUpdate(oldValue -> Math.min(oldValue, other.getMin()));
        max.getAndUpdate(oldValue -> Math.max(oldValue, other.getMax()));
        return this;
    }

    @Override
    public long getSum() {
        return sum.longValue();
    }

    @Override
    public long getCount() {
        return count.longValue();
    }

    @Override
    public int getMin() {
        return min.get();
    }

    @Override
    public int getMax() {
        return max.get();
    }

    public double getAverage() {
        long count = this.count.longValue();
        return count == 0 ? 0 : sum.doubleValue() / count;
    }
}
