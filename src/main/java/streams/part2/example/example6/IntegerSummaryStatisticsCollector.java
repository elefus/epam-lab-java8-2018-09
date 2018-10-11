package streams.part2.example.example6;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

public interface IntegerSummaryStatisticsCollector extends Collector<Integer, IntegerSummaryStatistics, IntegerSummaryStatistics> {

    @Override
    default BiConsumer<IntegerSummaryStatistics, Integer> accumulator() {
        return IntegerSummaryStatistics::accept;
    }

    @Override
    default BinaryOperator<IntegerSummaryStatistics> combiner() {
        return IntegerSummaryStatistics::merge;
    }

    @Override
    default Function<IntegerSummaryStatistics, IntegerSummaryStatistics> finisher() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}
