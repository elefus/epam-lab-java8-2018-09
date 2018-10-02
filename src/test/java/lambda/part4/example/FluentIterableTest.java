package lambda.part4.example;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
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

    @Test
    void name() {

    }

    @Test
    void name2() {

    }

    @Test
    void name3() {

    }
}