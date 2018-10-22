package spliterators.exercise;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Exercise1 {

    @Test
    void testSumUsingSpliterator() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 1)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);

        IntStream stream = StreamSupport.intStream(new FairRectangleSpliterator(data), false);

        assertThat(stream.sum(), is(50));
    }

    @Test
    void testMinUsingSpliterator() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 42)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);
        data[0][0] = 0;

        IntStream stream = StreamSupport.intStream(new FairRectangleSpliterator(data), false);

        assertThat(stream.min().orElseThrow(IllegalStateException::new), is(0));
    }

    @Test
    void testMaxUsingSpliterator() {
        int[][] data = Stream.generate(() -> IntStream.generate(() -> 42)
                                                      .limit(5)
                                                      .toArray())
                             .limit(10)
                             .toArray(int[][]::new);
        data[3][3] = 50;

        IntStream stream = StreamSupport.intStream(new FairRectangleSpliterator(data), true);

        assertThat(stream.max().orElseThrow(IllegalStateException::new), is(50));
    }
}
