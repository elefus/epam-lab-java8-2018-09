package streams.part2.example.example6.benchmarks.atomic;

import streams.part2.example.example6.impl.atomic.AtomicIntegerSummaryStatisticsCollector;
import streams.part2.example.example6.IntegerSummaryStatisticsCollector;
import streams.part2.example.example6.benchmarks.OnRandomUnorderedStreamBenchmark;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class AtomicOnRandomUnorderedStreamBenchmark extends OnRandomUnorderedStreamBenchmark {

    @Override
    public Supplier<IntegerSummaryStatisticsCollector> statisticsCollectorFactory() {
        return AtomicIntegerSummaryStatisticsCollector::new;
    }
}
