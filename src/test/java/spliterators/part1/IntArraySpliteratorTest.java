package spliterators.part1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;

class IntArraySpliteratorTest {

    @Test
    void sequential() {
        int[] data = {0, 1, 2, 3, 4, 5, 6, 7};
        IntArraySpliterator spliterator = new IntArraySpliterator(data);

        int result = StreamSupport.intStream(spliterator, false)
                                  .filter(val -> val > 3)
                                  .sum();

        assertThat(result, Matchers.is(22));
    }

    @Test
    void parallel() {
        int[] data = ThreadLocalRandom.current().ints(250_000).toArray();
        IntArraySpliterator spliterator = new IntArraySpliterator(data);
        int expected = IntStream.of(data).sum();

        int result = StreamSupport.intStream(spliterator, true)
                                  .sum();

        assertThat(result, Matchers.is(expected));
    }
}