package streams.part2.example.example6.impl.atomic;

import streams.part2.example.example6.IntegerSummaryStatistics;
import streams.part2.example.example6.IntegerSummaryStatisticsCollector;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Supplier;

public class AtomicIntegerSummaryStatisticsCollector implements IntegerSummaryStatisticsCollector {

    @Override
    public Supplier<IntegerSummaryStatistics> supplier() {
        return AtomicIntegerSummaryStatistics::new;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.allOf(Characteristics.class);
    }
}
