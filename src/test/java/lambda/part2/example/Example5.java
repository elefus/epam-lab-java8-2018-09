package lambda.part2.example;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr", "SameParameterValue"})
class Example5 {

    // TODO functional descriptor
    private static String sumWithDelimiter(String left, String delimiter, String right) {
        return left + delimiter + right;
    }

    // TODO functional descriptor
    private static Function<String, Function<String, Function<String, String>>> curriedSumWithDelimiter() {
        return left -> delimiter -> right -> sumWithDelimiter(left, delimiter, right);
    }

    @Test
    void testCurriedSumWithDelimiter() {
        Function<String, Function<String, Function<String, String>>> curriedSumWithDelimiter = curriedSumWithDelimiter();

        assertThat(curriedSumWithDelimiter.apply("a").apply("-").apply("b"), is("a-b"));
    }

    // TODO functional descriptor
    private static Function<String, Function<String, String>> partiallyAppliedSumWithDelimiter(
            Function<String, Function<String, Function<String, String>>> summator,
            String delimiter) {
        return left -> right -> summator.apply(left).apply(delimiter).apply(right);
    }

    @Test
    void testPartiallyAppliedSumWithDelimiter() {
        Function<String, Function<String, Function<String, String>>> summator = curriedSumWithDelimiter();
        Function<String, Function<String, String>> summatorWithDash = partiallyAppliedSumWithDelimiter(summator, "-");

        assertThat(summatorWithDash.apply("a").apply("b"), is("a-b"));
    }
}
