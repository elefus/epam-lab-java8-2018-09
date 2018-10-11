package streams.part2.example.example6;

import java.util.function.Consumer;

public interface IntegerSummaryStatistics extends Consumer<Integer> {

    long getSum();

    long getCount();

    int getMin();

    int getMax();

    double getAverage();

    IntegerSummaryStatistics merge(IntegerSummaryStatistics other);
}
