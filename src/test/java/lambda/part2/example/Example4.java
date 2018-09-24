package lambda.part2.example;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/*
    String s1 = someOperation1();
    String s2 = someOperation2();
    ...
    String result = concat(s1, s2);
 */
@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr"})
class Example4 {


//    int fib(0) -> 0;
//    int fib(1) -> 1;
//
//    int fib(int n) {
//        return fib(n - 1) + fib(n - 2);
//    }

    // (int, int, int) → int
    private static int sum(int x, int y, int z) {
        return x + y + z;
    }

    // () → Integer → Integer → Integer → Integer
    private static Function<Integer, Function<Integer, Function<Integer, Integer>>> curriedSum3UsingFunction() {
        return x -> y -> z -> sum(x, y, z);
    }

    @Test
    void testCurriedSum3UsingFunction() {
        int result = sum(1, 2, 3);
        assertThat(result, is(6));

        Function<Integer, Function<Integer, Function<Integer, Integer>>> curried3sum = curriedSum3UsingFunction();

        assertThat(curried3sum.apply(2).apply(2).apply(2), is(6));
    }

    // () → int → int → int → int
    private static IntFunction<IntFunction<IntUnaryOperator>> curriedSum3UsingIntFunction() {
        return x -> y -> z -> sum(x, y, z);
    }

    @Test
    void testCurriedSum3UsingIntFunction() {
        IntFunction<IntFunction<IntUnaryOperator>> curried3sum = curriedSum3UsingIntFunction();

        assertThat(curried3sum.apply(1).apply(2).applyAsInt(3), is(6));
    }

    // (String, String) → String
    private static String sum(String left, String right) {
        return left + right;
    }

    // () → String → String
    private static Function<String, Function<String, String>> curriedRevertedSumString() {
        return right -> left -> sum(left, right);
    }

    @Test
    void testCurriedRevertedSumString() {
        Function<String, Function<String, String>> curriedRevertedSum = curriedRevertedSumString();

        assertThat(curriedRevertedSum.apply("b").apply("a"), is("ab"));
    }
}
