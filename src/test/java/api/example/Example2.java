package api.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.nullsFirst;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@SuppressWarnings({"Java8ListSort", "Convert2Lambda", "ComparatorCombinators"})
class Example2 {

    @Test
    void sortWhitComparatorUsingJava7() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 6, 5);

        Collections.sort(values, new Comparator<Integer>() {
            @Override
            public int compare(Integer left, Integer right) {
                return Integer.compare(right, left);
            }
        });

        assertThat(values, contains(6, 5, 4, 3, 2, 1));
    }

    @Test
    void sortWhitLambdaComparatorUsingJava8() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);

        Collections.sort(values, (left, right) -> Integer.compare(right, left));

        assertThat(values, contains(6, 5, 4, 3, 2, 1));
    }

    @Test
    void sortWhitIntegerComparatorUsingJava8() {
        List<Person> people = getPersons();

        Collections.sort(people, (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));

        assertThat(people, contains(people.get(0), people.get(2), people.get(1), people.get(3)));
    }

    @Test
    void sortWhitKeyExtractorComparatorUsingJava8() {
        List<Person> people = getPersons();

        people.sort(comparing(Person::getFirstName));

        assertThat(people, contains(people.get(0), people.get(2), people.get(1), people.get(3)));
    }

    @Test
    void sortWithCombinedKeyExtractorComparatorUsingJava8() {
        List<Person> people = getPersons();

        people.sort(comparing(Person::getLastName).thenComparing(Person::getFirstName)
                                                  .thenComparing(Person::getAge));

        assertThat(people, contains(people.get(3), people.get(0), people.get(1), people.get(2)));
    }

    @Test
    void sortWhitIntValueKeyExtractorComparatorUsingJava8() {
        List<Person> people = getPersons();

        people.sort(comparingInt(Person::getAge));

        assertThat(people, contains(people.get(0), people.get(2), people.get(1), people.get(3)));

    }

    @Test
    void sortWhitFirstNullValuesComparatorUsingJava8() {
        List<Person> people = getPersons();
        people.set(2, null);

        people.sort(nullsFirst(comparing(Person::getFirstName)));

        assertThat(people, contains(people.get(0), people.get(1), people.get(3)));
    }

    private static List<Person> getPersons() {
        return Arrays.asList(
            new Person("Иван", "Литовцев", 15),
            new Person("Кирилл", "Литовцев", 41),
            new Person("Ирина", "Семенченко", 27),
            new Person("Николай", "Дмитриев", 60)
        );
    }
}
