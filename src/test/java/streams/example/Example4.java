//package streams.example;
//
//import org.junit.Test;
//
//import java.util.Comparator;
//import java.util.DoubleSummaryStatistics;
//import java.util.OptionalDouble;
//import java.util.OptionalInt;
//import java.util.OptionalLong;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.stream.DoubleStream;
//import java.util.stream.IntStream;
//import java.util.stream.LongStream;
//import java.util.stream.Stream;
//
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.*;
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//public class Example4 {
//
//    @Test
//    public void rangedIntStream() {
//        int[] values = IntStream.range(0, 5).toArray();
//
//        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, values);
//    }
//
//    @Test
//    public void closedRangedIntStream() {
//        int[] values = IntStream.rangeClosed(0, 5).toArray();
//
//        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5}, values);
//    }
//
//    @Test
//    public void generatedDoubleStream() {
//        long count = DoubleStream.iterate(0.5, value -> value + 0.5)
//                                 .limit(5)
//                                 .count();
//
//        assertEquals(5, count);
//    }
//
//    @Test
//    public void sumMethodInLongAndIntStreams() {
//        long resultLong = LongStream.range(0, 50)
//                                    .sum();
//
//        assertEquals(1225, resultLong);
//    }
//
//    @Test
//    public void overflowInSumMethodIsUncontrolled() {
//        int resultInt = IntStream.range(0, 10_000_000)
//                                 .sum();
//
//        assertEquals(-2014260032, resultInt);
//    }
//
//    @Test(expected = ArithmeticException.class)
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public void controlledSumUsingReduce() {
//        IntStream.range(0, 10_000_000)
//                 .reduce(0, Math::addExact);
//    }
//
//    @Test
//    @SuppressWarnings("ConstantConditions")
//    public void primitiveOptional() {
//        OptionalInt result = IntStream.empty()
//                                      .reduce(Integer::sum);
//
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void calcAverageInStream() {
//        DoubleStream randomDoubles = ThreadLocalRandom.current()
//                                                      .doubles(10, 0, 10);
//
//        OptionalDouble optionalResult = randomDoubles.average();
//        double result = optionalResult.orElseThrow(IllegalStateException::new);
//
//        assertTrue(result >= 0);
//        assertTrue(result <= 100);
//    }
//
//    @Test
//    public void findMinimumInPrimitiveStream() {
//        OptionalInt result = IntStream.of(1, 0, -5, 3)
//                                      .min();
//
//        assertEquals(-5, result.orElseThrow(IllegalStateException::new));
//    }
//
//    @Test
//    public void findMaximumInPrimitiveStream() {
//        OptionalLong result = LongStream.of(-7, 1, 0, 6)
//                                        .max();
//
//        assertEquals(6, result.orElseThrow(IllegalStateException::new));
//    }
//
//    @Test
//    public void summaryStatisticsOfPrimitiveStream() {
//        DoubleSummaryStatistics statistics = DoubleStream.of(0.5, 0.33, Math.PI, 60.3)
//                                                         .summaryStatistics();
//
//        assertEquals(4, statistics.getCount());
//        assertEquals(60.3, statistics.getMax(), Math.ulp(60.3));
//        assertEquals(0.33, statistics.getMin(), Math.ulp(0.33));
//        assertEquals(16.067, statistics.getAverage(), 0.001);
//        assertEquals(64.271, statistics.getSum(), 0.001);
//    }
//
//    @Test
//    public void boxingPrimitiveStream() {
//        IntStream original = IntStream.range(0, 10);
//
//        Stream<Integer> result = original.boxed();
//
//        assertEquals(10, result.count());
//    }
//
//    @Test
//    public void convertGenericStreamToPrimitive() {
//        Stream<String> original = Stream.of("1", "2", "10", "20");
//
//        int sum = original.mapToInt(Integer::parseInt)
//                          .sum();
//
//        assertEquals(33, sum);
//    }
//
//
//    @Test
//    public void convertPrimitiveStreamToGeneric() {
//        IntStream original = IntStream.range(0, 10);
//
//        String result = original.mapToObj(String::valueOf)
//                                .max(Comparator.naturalOrder())
//                                .orElseThrow(IllegalStateException::new);
//
//        assertEquals("9", result);
//    }
//}
