package spliterators.part2;

import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

class UnfairRectangleSpliteratorTest {

    @Test
    void name() {
        int[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };

        UnfairRectangleSpliterator spliterator = UnfairRectangleSpliterator.of(data);
        StreamSupport.intStream(spliterator, false)
                     .forEachOrdered(System.out::println);

    }
}
