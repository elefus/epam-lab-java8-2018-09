package streams.part2.example.example6.benchmarks.primitive;

import streams.part2.example.example6.IntegerSummaryStatisticsCollector;
import streams.part2.example.example6.impl.primitive.PrimitiveIntegerSummaryStatisticsCollector;
import streams.part2.example.example6.benchmarks.OnRandomOrderedStreamBenchmark;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PrimitiveOnRandomOrderedStreamBenchmark extends OnRandomOrderedStreamBenchmark {

    @Override
    public Supplier<IntegerSummaryStatisticsCollector> statisticsCollectorFactory() {
        return PrimitiveIntegerSummaryStatisticsCollector::new;
    }
}
