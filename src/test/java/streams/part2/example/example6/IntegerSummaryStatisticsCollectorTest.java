package streams.part2.example.example6;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import streams.part2.example.example6.impl.atomic.AtomicIntegerSummaryStatisticsCollector;
import streams.part2.example.example6.impl.primitive.PrimitiveIntegerSummaryStatisticsCollector;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;

class IntegerSummaryStatisticsCollectorTest {


    @Test
    void test1() {
        IntegerSummaryStatistics collect = IntStream.iterate(1, val -> val ^ 1)
                                                    .limit(10)
                                                    .parallel()
                                                    .boxed()
                                                    .collect(new AtomicIntegerSummaryStatisticsCollector());

        assertThat(collect.getCount(), Matchers.is(10L));
        assertThat(collect.getSum(), Matchers.is(5L));
        assertThat(collect.getAverage(), Matchers.is(0.5));
        assertThat(collect.getMax(), Matchers.is(1));
        assertThat(collect.getMin(), Matchers.is(0));
    }

    @Test
    void test2() {
        IntegerSummaryStatistics collect = IntStream.iterate(1, val -> val ^ 1)
                                                    .limit(10)
                                                    .parallel()
                                                    .boxed()
                                                    .collect(new PrimitiveIntegerSummaryStatisticsCollector());

        assertThat(collect.getCount(), Matchers.is(10L));
        assertThat(collect.getSum(), Matchers.is(5L));
        assertThat(collect.getAverage(), Matchers.is(0.5));
        assertThat(collect.getMax(), Matchers.is(1));
        assertThat(collect.getMin(), Matchers.is(0));
    }
}