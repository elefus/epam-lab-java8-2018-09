package lambda.part1.example;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"unused", "ComparatorCombinators"})
class Example11 {

    private static void method(String str) {
        throw new RuntimeException();
    }

    @SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
    private static Consumer<String> anonymousConsumer() {
        return new Consumer<String>() {
            @Override
            public void accept(String string) {
                method(string);
            }
        };
    }

    @SuppressWarnings("Convert2MethodRef")
    private static Consumer<String> lambdaConsumer() {
        return string -> method(string);
    }

    private static Consumer<String> referenceConsumer() {
        return Example11::method;
    }

    @SuppressWarnings("Convert2MethodRef")
    private static Supplier<Integer> lengthExtractor(String str) {
        return () -> str.length();
    }

    private final String str = "123";

    private Supplier<String> getString() {
        return () -> str;
    }

    @Test
    void test() {
        Consumer<String> consumer1 = anonymousConsumer();
        Consumer<String> consumer2 = anonymousConsumer();
        assertThat(consumer1, not(sameInstance(consumer2)));

        Consumer<String> consumer3 = lambdaConsumer();
        Consumer<String> consumer4 = lambdaConsumer();
        assertThat(consumer3, is(sameInstance(consumer4)));

        Consumer<String> consumer5 = referenceConsumer();
        Consumer<String> consumer6 = referenceConsumer();
        assertThat(consumer5, is(sameInstance(consumer6)));

        Supplier<Integer> supplier1 = lengthExtractor("qwe");
        Supplier<Integer> supplier2 = lengthExtractor("abc");
        assertThat(supplier1, not(sameInstance(supplier2)));

        Supplier<String> string1 = getString();
        Supplier<String> string2 = getString();
        assertThat(string1, not(sameInstance(string2)));
    }
}
