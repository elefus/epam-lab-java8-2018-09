package api.example;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

class Example1 {

    @Test
    @SuppressWarnings("ForLoopReplaceableByForEach")
    void foriLoop() {
        List<String> list = new LinkedList<>(Arrays.asList("1", "2", "3"));


        for (int i = 0; i < list.size(); ++i) {
            assertThat(list.get(i).length(), Matchers.lessThan(2));
        }
    }

    @Test
    void foreachLoop() {
        Iterable<String> list = new LinkedList<>(Arrays.asList("1", "2", "3"));

        for (String element : list) {
            assertThat(element.length(), Matchers.lessThan(2));
        }
    }

    @Test
    void foreachMethod() {
        Iterable<String> list = new LinkedList<>(Arrays.asList("1", "2", "3"));

        list.forEach(element -> assertThat(element.length(), Matchers.lessThan(2)));
    }
}
