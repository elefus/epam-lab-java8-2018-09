package streams.part1.example;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Example4 {

    @Test
    void rangedIntStream() {
        int[] values = IntStream.range(0, 5).toArray();

        assertThat(values, is(new int[]{0, 1, 2, 3, 4}));
    }

    @Test
    void closedRangedIntStream() {
        int[] values = IntStream.rangeClosed(0, 5).toArray();

        assertThat(values, is(new int[]{0, 1, 2, 3, 4, 5}));
    }

    @Test
    void generatedDoubleStream() {
        long count = DoubleStream.iterate(0.5, value -> value + 0.5)
                                 .limit(5)
                                 .count();

        assertThat(count, is(5L));
    }

    @Test
    void sumMethodInLongAndIntStreams() {
        long resultLong = LongStream.range(0, 50)
                                    .sum();

        assertThat(resultLong, is(1225L));
    }

    @Test
    void overflowInSumMethodIsUncontrolled() {
        int resultInt = IntStream.range(0, 10_000_000)
                                 .sum();

        assertThat(resultInt, is(-2014260032));
    }

    @Test
    void controlledSumUsingReduce() {
        assertThrows(ArithmeticException.class, () -> IntStream.range(0, 10_000_000)
                                                               .reduce(0, Math::addExact));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void primitiveOptional() {
        OptionalInt result = IntStream.empty()
                                      .reduce(Integer::sum);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void calcAverageInStream() {
        DoubleStream randomDoubles = ThreadLocalRandom.current()
                                                      .doubles(10, 0, 10);

        double result = randomDoubles.sum();

        assertThat(result, both(greaterThanOrEqualTo(0d)).and(lessThanOrEqualTo(100d)));
    }

    @Test
    void findMinimumInPrimitiveStream() {
        OptionalInt result = IntStream.of(1, 0, -5, 3)
                                      .min();

        assertThat(result.orElseThrow(IllegalStateException::new), is(-5));
    }

    @Test
    void findMaximumInPrimitiveStream() {
        OptionalLong result = LongStream.of(-7, 1, 0, 6)
                                        .max();

        assertThat(result.orElseThrow(IllegalStateException::new), is(6L));
    }

    @Test
    void summaryStatisticsOfPrimitiveStream() {
        DoubleSummaryStatistics statistics = DoubleStream.of(0.5, 0.33, Math.PI, 60.3)
                                                         .summaryStatistics();

        assertThat(statistics.getCount(), is(4L));
        assertThat(60.3,  closeTo(statistics.getMax(), 0.001));
        assertThat(0.33, closeTo(statistics.getMin(), 0.001));
        assertThat(16.067, closeTo(statistics.getAverage(), 0.001));
        assertThat(64.271, closeTo(statistics.getSum(), 0.001));
    }

    @Test
    void boxingPrimitiveStream() {
        IntStream original = IntStream.range(0, 10);

        Stream<Integer> result = original.boxed();

        assertThat(result.count(), is(10L));
    }

    @Test
    void convertGenericStreamToPrimitive() {
        Stream<String> original = Stream.of("1", "2", "10", "20");

        int sum = original.mapToInt(Integer::parseInt)
                          .sum();

        assertThat(sum, is(33));
    }


    @Test
    void convertPrimitiveStreamToGeneric() {
        IntStream original = IntStream.range(0, 10);

        String result = original.mapToObj(String::valueOf)
                                .max(Comparator.naturalOrder())
                                .orElseThrow(IllegalStateException::new);

        assertThat(result, is("9"));
    }
}
