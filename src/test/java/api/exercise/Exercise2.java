package api.exercise;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.BinaryOperator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
class Exercise2 {

    @Test
    void sequentialPrefixTestSum() {
        Integer[] original = {1, 2, 3, 4, 5, 6, 7, 8};

        Integer[] result = sequentialPrefix(original, Integer::sum);

        assertThat(original, Matchers.not(Matchers.sameInstance(result)));
        assertThat(result, Matchers.arrayContaining(1, 3, 6, 10, 15, 21, 28, 36));
    }

    @Test
    void sequentialPrefixTestMult() {
        Integer[] original = {1, 2, 3, 4, 5, 6, 7, 8};

        Integer[] result = sequentialPrefix(original, Math::multiplyExact);

        assertThat(original, Matchers.not(Matchers.sameInstance(result)));
        assertThat(result, Matchers.arrayContaining(1, 2, 6, 24, 120, 720, 5_040, 40_320));
    }

    @Test
    void sequentialPrefixTestLiningByMax() {
        Integer[] original = {1, 2, 3, 2, 1, 5, 7, 5};

        Integer[] result = sequentialPrefix(original, Integer::max);

        assertThat(original, Matchers.not(Matchers.sameInstance(result)));
        assertThat(result, Matchers.arrayContaining(1, 2, 3, 3, 3, 5, 7, 7));
    }

    /**
     * Выполняет операцию сканирования в однопоточном режиме.
     * Не модифицирует исходный набор данных.
     *
     * @param source   Массив исходных элементов.
     * @param operator Оператор сканирования.
     * @return Результат сканирования.
     * @see <a href="https://habr.com/company/epam_systems/blog/247805">Сканирование</a>
     */
    private static <T> T[] sequentialPrefix(T[] source, BinaryOperator<T> operator) {
        T[] result = Arrays.copyOf(source, source.length);
        for (int i = 1; i < result.length; i++) {
            result[i] = operator.apply(result[i - 1], result[i]);
        }
        return result;
    }

    @Test
    void log2TestNormalCases() {
        assertThat(log2(1), is(0));
        assertThat(log2(2), is(1));
        assertThat(log2(3), is(1));
        assertThat(log2(4), is(2));
        assertThat(log2(Integer.MAX_VALUE), is(30));
    }

    @Test
    void log2WithZeroValueShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> log2(0));
    }

    @Test
    void log2WithNegativeValuesShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> log2(Integer.MIN_VALUE));
    }

    /**
     * Вычисляет двоичный логарифм положительного числа.
     *
     * @param value Аргумент.
     * @return Логарифм по основанию 2 от аргумента.
     * @throws IllegalArgumentException Если {@code value <= 0}
     */
    private static int log2(int value) throws IllegalArgumentException {
        if (value > 0) {
            return Integer.numberOfTrailingZeros(Integer.highestOneBit(value));
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Test
    void powTestNormalCases() {
        assertThat(pow(0, 0), is(1));
        assertThat(pow(1, 0), is(1));
        assertThat(pow(1, 1), is(1));
        assertThat(pow(5, 1), is(5));
        assertThat(pow(5, 2), is(25));
        assertThat(pow(10, 3), is(1000));
    }

    @Test
    void powWithNegativeBaseShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> pow(-1, 1));
    }

    @Test
    void powWithNegativeDegreeShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> pow(0, -1));
    }

    /**
     * Возводит неотрицательное число в неотрицательную степень.
     *
     * @param base   Основание степени.
     * @param degree Показатель степени.
     * @return Значение {@code base}<sup>{@code degree}</sup>
     * @throws IllegalArgumentException Если {@code base < 0} или {@code degree < 0}
     */
    private static int pow(int base, int degree) throws IllegalArgumentException {
        if (base >= 0 && degree >= 0) {
            if (degree > 0) {
                int result = 1;
                for (int i = 0; i < degree; i++) {
                    result *= base;
                }
                return result;
            } else {
                return 1;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
