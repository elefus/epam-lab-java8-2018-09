package lambda.part4.example;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyArray;

class FluentIterableTest {

    @Nested
    class Filter {

        @Test
        void emptyCollection() {
            FluentIterable<Integer> source = FluentIterable.of();

            Integer[] result = source.filter(value -> value < 3)
                                     .filter(value -> value > 2)
                                     .toArray(Integer[]::new);


            assertThat(result, emptyArray());
        }


        @Test
        void sourceContainingNotMatchingElements() {
            FluentIterable<Integer> source = FluentIterable.of(1, 2, 3, 4, 5);

            Integer[] result = source.filter(value -> value < 3)
                                     .toArray(Integer[]::new);


            assertThat(result, arrayContaining(1, 2));
        }

        @Test
        void sourceNotContainingNotMatchingElements() {
            FluentIterable<Integer> source = FluentIterable.of(1, 2, 3, 4, 5);

            Integer[] result = source.filter(value -> value > 0)
                                     .toArray(Integer[]::new);


            assertThat(result, arrayContaining(1, 2, 3, 4, 5));
        }
    }

    @Nested
    class Skip {

        @Test
        void onEmptySource() {
            FluentIterable<Object> source = FluentIterable.of();

            List<Object> result = source.skip(10).toList();

            assertThat(result, empty());
        }

        @Test
        void whenSkip0() {
            FluentIterable<Integer> source = FluentIterable.of(1, 2, 3);

            List<Integer> result = source.skip(0).toList();

            assertThat(result, contains(1, 2, 3));
        }

        @Test
        void whenSkipMoreThanPresent() {
            FluentIterable<Integer> source = FluentIterable.of(1, 2, 3);

            List<Integer> result = source.skip(10).toList();

            assertThat(result, empty());
        }

        @Test
        void whenSkipLessThanPresent() {
            FluentIterable<Integer> source = FluentIterable.of(1, 2, 3);

            List<Integer> result = source.skip(1).toList();

            assertThat(result, contains(2, 3));
        }
    }
}