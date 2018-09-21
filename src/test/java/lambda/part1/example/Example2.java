package lambda.part1.example;

import interfaces.Summator;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
class Example2 {

    @Test
    void anonymousClass() {
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
    void statementLambda() {
        Summator<Integer> summator = (left, right) -> {
            return left + right;
        };

        assertThat(summator.sum(1, 2), is(3));
        assertThat(summator.sum(-1, 2), is(1));
        assertThat(summator.twice(2), is(2));
        assertThat(summator.twice(0), is(0));
    }

    @Test
    void expressionLambda() {
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
    void staticMethodReference() {
        Summator<String> summator = Example2::stringSum;

//        Comparator<Integer> intComparator = (a, b) -> Integer.compare(a, b);
        Comparator<Integer> intComparator = Integer::compare;

        assertThat(summator.sum("a", "b"), is("ab"));
        assertThat(summator.twice("a"), is("aa"));
    }

    private final String delimiter = "-";

    private String stringSumWithDelimiter(String left, String right) {
        return left + delimiter + right;
    }

    @Test
    void objectMethodReference() {
        Example2 example2 = new Example2();

        Summator<String> summator = example2::stringSumWithDelimiter;



        assertThat(summator.sum("a", "b"), is("a-b"));
        assertThat(summator.twice("a"), is("a-a"));
    }

    @Test
    void typeMethodReference() {
        String str = "hello";

        Runnable task = str::length;
        task.run();

        Supplier<Integer> getStrLength = str::length;
        getStrLength.get();


        Function<String, Integer> getLength = String::length;
        Function<String, Integer> getLength1 = string -> string.length();

        getLength.apply("123");
        getLength.apply(str);

        assertThat(getLength.apply("123"), is(3));
    }

    @Test
    void typeInferenceInLambda() {
        Summator<Integer> summator = Example2::sum;

        process(summator);

        // FIXME type inference
//        process((left, right) -> Example2.sum(left, right));
        process((Integer left, Integer right) -> Example2.sum(left, right));
        process((left, right) -> Example2.sum((Integer)left, (Integer)right));
        Example2.<Integer>process((left, right) -> Example2.sum(left, right));
    }

    private static Integer sum(Integer left, Integer right) {
        return left + right;
    }

    private static <T extends Number> void process(Summator<T> summator) {

    }
}
