package api.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SuppressWarnings({"Java8ListSort", "ForLoopReplaceableByForEach", "Java8CollectionRemoveIf"})
class Example3 {

    @Test
    void removeIfUsingJava7() {
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        for (Iterator<Integer> iterator = values.iterator(); iterator.hasNext(); ) {
            Integer value = iterator.next();
            if (value < 3) {
                iterator.remove();
            }
        }

        assertThat(values, contains(3, 4, 5, 6));
    }

    @Test
    void removeIfUsingJava8() {
        Collection<Integer> values = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        values.removeIf(value -> value < 3);

        assertThat(values, contains(3, 4, 5, 6));
    }

    @Test
    void replaceAllUsingJava7() {
        List<Integer> values = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        ListIterator<Integer> iterator = values.listIterator();
        while (iterator.hasNext()) {
            iterator.set(iterator.next() + 1);
        }

        assertThat(values, contains(2, 3, 4, 5, 6, 7));
    }

    @Test
    void replaceAllUsingJava8() {
        List<Integer> values = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

        values.replaceAll(Math::incrementExact);

        assertThat(values, contains(2, 3, 4, 5, 6, 7));
    }

    @Test
    void sortUsingJava7() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "333", "22", "4444"));

        Collections.sort(values, Comparator.comparingInt(String::length));

        assertThat(values, contains("1", "22", "333", "4444"));
    }

    @Test
    void sortUsingJava8() {
        List<String> values = new ArrayList<>(Arrays.asList("1", "333", "22", "4444"));

        values.sort(Comparator.comparingInt(String::length));

        assertThat(values, contains("1", "22", "333", "4444"));
    }
}
