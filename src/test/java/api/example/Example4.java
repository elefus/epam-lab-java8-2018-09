package api.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

class Example4 {

    @Test
    void arraysParallelSortUsingJava8() {
        Integer[] values = {1, 6, 9, 4, -1, -4, 0, 2, 3};

        Arrays.parallelSort(values);

        assertThat(values, arrayContaining(-4, -1, 0, 1, 2, 3, 4, 6, 9));
    }

    /**
     * @see <a href="https://habrahabr.ru/company/epam_systems/blog/247805">Алгоритм параллельного сканирования</a>
     */
    @Test
    void arraysParallelPrefixUsingJava8() {
        Integer[] values = {1, 6, 9, 4, -1, -4, 0, 2, 3};

        Arrays.parallelPrefix(values, Integer::sum);

        assertThat(values, arrayContaining(1, 7, 16, 20, 19, 15, 15, 17, 20));
    }

    @Test
    void arraysParallelSetAllUsingJava8() {
        Integer[] values = new Integer[5];

        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
        }

        Arrays.parallelSetAll(values, operand -> operand + 1);
        assertThat(values, arrayContaining(1, 2, 3, 4, 5));

        // TODO set 0 for all even numbers для всех четных 0, для нечетных 1
        assertThat(values, arrayContaining(0, 1, 0, 1, 0));
    }
}
