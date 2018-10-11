package streams.part2.example.example6.impl.primitive;

import streams.part2.example.example6.IntegerSummaryStatistics;
import streams.part2.example.example6.IntegerSummaryStatisticsCollector;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class PrimitiveIntegerSummaryStatisticsCollector implements IntegerSummaryStatisticsCollector {

    @Override
    public Supplier<IntegerSummaryStatistics> supplier() {
        return PrimitiveIntegerSummaryStatistics::new;
    }
}
