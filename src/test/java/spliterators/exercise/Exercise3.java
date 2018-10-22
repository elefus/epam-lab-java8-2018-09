package spliterators.exercise;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;

class Exercise3 {

    @Test
    void test1() {
        Stream<String> left = Stream.of("a", "b", "c", "d");
        Stream<String> right = Stream.of("A", "B", "C", "D");


        List<Pair<String, String>> result = new AdvancedStreamImpl<>(left).zip(right)
                                                                          .collect(Collectors.toList());

        assertThat(result, contains(new Pair<>("a", "A"), new Pair<>("b", "B"), new Pair<>("c", "C"), new Pair<>("d", "D")));
    }

    @Test
    void test2() {
        Stream<String> left = Stream.of("a", "b", "c", "d");
        Stream<Integer> right = IntStream.range(10, 14).boxed();


        Map<Integer, String> result = new AdvancedStreamImpl<>(left).zip(right)
                                                                    .collect(toMap(Pair::getValue2, Pair::getValue1));

        assertThat(result, hasEntry(10, "a"));
        assertThat(result, hasEntry(11, "b"));
        assertThat(result, hasEntry(12, "c"));
        assertThat(result, hasEntry(13, "d"));
    }
}
