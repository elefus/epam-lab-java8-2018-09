package optional;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"OptionalIsPresent", "ConstantConditions", "Convert2MethodRef"})
class Example1 {

    @Test
    void ifPresent() {
        Optional<String> optional = Optional.of("string");

        List<String> result = new ArrayList<>();

        if (optional.isPresent()) {
            result.add(optional.get());
        }

        optional.ifPresent(result::add);

        assertThat(result, contains("string"));
    }

    @Test
    void orElse() {
        Optional<String> optional = Optional.empty();

        String result = optional.orElse("string");

        assertThat("string", is(result));
    }

    @Test
    void orElseThrow() {
        Optional<String> optional = Optional.empty();

        assertThrows(IllegalArgumentException.class, () -> optional.orElseThrow(IllegalArgumentException::new));
    }

    @Test
    void mapAndThenIfPresent() {
        Optional<String> optional = Optional.of("42");

        List<Integer> result = new ArrayList<>();
        optional.map(Integer::valueOf)
                .ifPresent(result::add);

        assertThat(result, contains(42));
    }



    @Test
    void mapFilterOrElse() {
        LinkedList<String> data = new LinkedList<>(Arrays.asList("qwe", "10", "30"));
        Optional<Deque<String>> optional = Optional.of(data);

        Predicate<String> isDigit = Pattern.compile("\\d+").asPredicate();
        Optional<Integer> result = optional.map(Deque::getFirst)
                                           .filter(isDigit)
                                           .map(Integer::valueOf);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    void flatMapOrElseGet() {
        Optional<String> optional = Optional.of("33");

        Integer result = optional.flatMap(Example1::convertStringToInteger)
                                 .orElseGet(Example1::hugeOperation);

        assertThat(result, is(33));
    }

    private static Optional<Integer> convertStringToInteger(String stringValue) {
        return Optional.ofNullable(stringValue)
                       .filter(Pattern.compile("\\d+").asPredicate())
                       .map(Integer::valueOf);
    }

    private static Integer hugeOperation() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
            return 42;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    void primitiveOptionals() {
        OptionalInt optional = OptionalInt.of(42);

        int result = optional.orElse(0);

        assertThat(result, is(42));
    }
}
