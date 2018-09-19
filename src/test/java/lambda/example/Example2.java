package lambda.example;

import interfaces.Summator;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
class Example2 {

    @Test
    void anonymousClassImplementation() {
        Summator<Integer> summator = new Summator<Integer>() {
            @Override
            public Integer sum(Integer left, Integer right) {
                return left + right;
            }
        };

        assertThat(summator.sum(1, 2), is(3));
        assertThat(summator.sum(-1, 2), is(1));
        assertThat(summator.twice(2), is(2));
        assertThat(summator.twice(0), is(0));
    }

    @Test
    void statementLambdaImplementation() {
        Summator<Integer> summator = (left, right) -> {
            return left + right;
        };

        assertThat(summator.sum(1, 2), is(3));
        assertThat(summator.sum(-1, 2), is(1));
        assertThat(summator.twice(2), is(2));
        assertThat(summator.twice(0), is(0));
    }

    @Test
    void expressionLambdaImplementation() {
        Summator<Integer> summator = (a, b) -> a + b;

        assertThat(summator.sum(1, 2), is(3));
        assertThat(summator.sum(-1, 2), is(1));
        assertThat(summator.twice(2), is(2));
        assertThat(summator.twice(0), is(0));
    }

    private static String stringSum(String left, String right) {
        return left + right;
    }

    @Test
    void classMethodReferenceLambdaImplementation() {
        Summator<String> summator = Example2::stringSum;

        assertThat(summator.sum("a", "b"), is("ab"));
        assertThat(summator.twice("a"), is("aa"));
    }

    private final String delimiter = "-";

    private String stringSumWithDelimiter(String left, String right) {
        return left + delimiter + right;
    }

    @Test
    void objectMethodReferenceLambdaImplementation() {
        Summator<String> summator = this::stringSumWithDelimiter;

        assertThat(summator.sum("a", "b"), is("a-b"));
        assertThat(summator.twice("a"), is("a-a"));
    }

    @Test
    void typeMethodReference() {
        Function<String, Integer> getLength = String::length;

        assertThat(getLength.apply("123"), is(3));
    }

    @Test
    void typeInferenceInLambda() {
        Summator<Integer> summator = Example2::sum;

        process(summator);

        // FIXME type inference
//        process((left, right) -> Example2.sum(left, right));
//        process((Integer left, Integer right) -> Example2.sum(left, right));
//        process((left, right) -> Example2.sum((Integer)left, (Integer)right));
//        Example2.<Integer>process((left, right) -> Example2.sum(left, right));
    }

    private static Integer sum(Integer left, Integer right) {
        return left + right;
    }

    private static <T extends Number> void process(Summator<T> summator) {

    }
}
