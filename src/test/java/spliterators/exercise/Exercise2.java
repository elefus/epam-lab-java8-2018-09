package spliterators.exercise;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;

class Exercise2 {

    @Test
    void test1() {
        Stream<String> original = Stream.of("Hello happy world!")
                                        .flatMap(Pattern.compile("\\s+")::splitAsStream)
                                        .map(String::toLowerCase);

        List<String> result = new AdvancedStreamImpl<>(original).takeWhile(word -> word.startsWith("h"))
                                                                .collect(Collectors.toList());

        assertThat(result, contains("hello", "happy"));
    }

    @Test
    void test2() {
        Stream<String> original = IntStream.rangeClosed(0, 20)
                                           .mapToObj(String::valueOf);

        String[] result = new AdvancedStreamImpl<>(original).takeWhile(word -> word.length() == 1)
                                                            .toArray(String[]::new);

        assertThat(result, arrayContaining("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }
}
