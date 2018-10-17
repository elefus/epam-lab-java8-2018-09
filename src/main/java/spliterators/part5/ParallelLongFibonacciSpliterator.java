package spliterators.part5;

import java.util.Comparator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class ParallelLongFibonacciSpliterator extends Spliterators.AbstractSpliterator<Long> {

    private static final int BOUND_INDEX = 150;
    private int index = 1;

    public static long[][] matrixMultiplication(long[][] a, long[][] b) {
        // [a00 * b00 + a01 * b10, a00 * b01 + a01 * b11]
        // [a10 * b00 + a11 * b10, a10 * b01 + a11 * b11]
        return new long[][]{
                {
                        a[0][0] * b[0][0] + a[0][1] * b[1][0],
                        a[0][0] * b[0][1] + a[0][1] * b[1][1]
                },
                {
                        a[1][0] * b[0][0] + a[1][1] * b[1][0],
                        a[1][0] * b[0][1] + a[1][1] * b[1][1]
                },
        };
    }

    // возведение матрицы размера 2 на 2 в степень n
    public static long[][] matrixPowerFast(long[][] a, int n) {
        if (n == 0) {
            // любая матрица в нулевой степени равна единичной матрице
            return new long[][]{
                    {1, 0},
                    {0, 1}
            };
        } else if (n % 2 == 0) {
            // a ^ (2k) = (a ^ 2) ^ k
            return matrixPowerFast(matrixMultiplication(a, a), n / 2);
        } else {
            // a ^ (2k + 1) = (a) * (a ^ 2k)
            return matrixMultiplication(matrixPowerFast(a, n - 1), a);
        }
    }

    // функция, возвращающая n-ое число Фибоначчи
    public static long fibonacci(int n) {
        if (n == 0) {
            return 0;
        }

        long[][] a = {
                {1, 1},
                {1, 0}
        };
        long[][] powerOfA = matrixPowerFast(a, n - 1);
        // nthFibonacci = powerOfA[0][0] * F_1 + powerOfA[0][0] * F_0 = powerOfA[0][0] * 1 + powerOfA[0][0] * 0
        return powerOfA[0][0];
    }


    public ParallelLongFibonacciSpliterator() {
        super(93, ORDERED | DISTINCT | SORTED | SIZED | NONNULL | IMMUTABLE);
    }

    @Override
    public boolean tryAdvance(Consumer<? super Long> action) {
        if (index == BOUND_INDEX) {
            return false;
        }
        action.accept(fibonacci(index++));
        return true;
    }

    @Override
    public Comparator<? super Long> getComparator() {
        return null;
    }

    @Override
    public long estimateSize() {
        return BOUND_INDEX - index;
    }
}

