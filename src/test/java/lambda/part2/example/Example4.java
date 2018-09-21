package lambda.part2.example;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.IntFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr"})
class Example4 {

    // TODO functional descriptor
    private static int sum(int x, int y, int z) {
        return x + y + z;
    }

    // TODO functional descriptor
    private static Function<Integer, Function<Integer, Function<Integer, Integer>>> curriedSum3UsingFunction() {
        return x -> y -> z -> sum(x, y, z);
    }

    @Test
    void testCurriedSum3UsingFunction() {
        int result = sum(1, 2, 3);
        assertThat(result, is(6));

        Function<Integer, Function<Integer, Function<Integer, Integer>>> curried3sum = curriedSum3UsingFunction();
//        assertThat(curried3sum..., is(6));
    }

    // TODO functional descriptor
    private static IntFunction<IntFunction<IntFunction<Integer>>> curriedSum3UsingIntFunction() {
        return x -> y -> z -> sum(x, y, z);
    }

    @Test
    void testCurriedSum3UsingIntFunction() {
        IntFunction<IntFunction<IntFunction<Integer>>> curried3sum = curriedSum3UsingIntFunction();

        assertThat(curried3sum.apply(1).apply(2).apply(3), is(6));
    }

    // TODO functional descriptor
    private static String sum(String left, String right) {
        return left + right;
    }

    private static Function<String, Function<String, String>> curriedRevertedSumString() {
        return right -> left -> sum(left, right);
    }

    @Test
    void testCurriedRevertedSumString() {
        Function<String, Function<String, String>> curriedRevertedSum = curriedRevertedSumString();

        assertThat(curriedRevertedSum.apply("b").apply("a"), is("ab"));
    }
}
