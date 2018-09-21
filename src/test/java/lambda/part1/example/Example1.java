package lambda.part1.example;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
class Example1 {

    @Test
    void sortPersonsByLastNameUsingArraysSortUsingLocalComparator() {
        Person[] persons = getPersons();

        class ComparatorByLastName implements Comparator<Person> {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        }

        Arrays.sort(persons, new ComparatorByLastName());

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }


    @Test
    void sortPersonsByLastNameUsingArraysSortUsingAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @Test
    void findFirstAlexUsingForeach() {
        Person[] persons = getPersons();

        Person person = null;
        for (Person current : persons) {
            if ("Алексей".equals(current.getFirstName())) {
                person = current;
                break;
            }
        }

        assertThat(person, is(new Person("Алексей", "Доренко", 40)));
    }

    /**
     * Google Guava – библиотека для Java, предоставляющая дополнительные возможности при работе с коллекциями, IO, кэшами.
     *
     * @see <a href="https://github.com/google/guava">Официальный сайт проекта Guava</a>
     */
    @Test
    void findFirstAlexUsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> isFirstNameAlexChecker = new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return "Алексей".equals(person.getFirstName());
            }
        };
        Optional<Person> personOptional = FluentIterable.from(persons)
                                                        .firstMatch(isFirstNameAlexChecker);

        assertThat(personOptional.isPresent(), is(true));
        assertThat(personOptional.get(), is(new Person("Алексей", "Доренко", 40)));
    }

    @Test
    void findFirstAlexUsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> personOptional = FluentIterable.from(persons)
                                                        .firstMatch(new Predicate<Person>() {
                                                            @Override
                                                            public boolean apply(Person p) {
                                                                return "Алексей".equals(p.getFirstName());
                                                            }
                                                        });

        assertThat(personOptional.isPresent(), is(true));
        assertThat(personOptional.get(), is(new Person("Алексей", "Доренко", 40)));
    }

    @Test
    void createMapFromListUsingForeach() {
        Map<String, Person> personByLastName = new HashMap<>();

        for (Person person : getPersons()) {
            personByLastName.put(person.getLastName(), person);
        }

        assertThat(personByLastName, is(new HashMap<String, Person>() {{
            put("Мельников", new Person("Иван", "Мельников", 20));
            put("Доренко", new Person("Алексей", "Доренко", 40));
            put("Зимов", new Person("Николай", "Зимов", 30));
        }}));
    }

    @Test
    void createMapFromListUsingGuavaFunction() {
        List<Person> persons = Arrays.asList(getPersons());

        Function<Person, String> getLastNameFromPerson = new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.getLastName();
            }
        };
        // Упадет, если одному ключу будет соответствовать два элемента.
        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(getLastNameFromPerson);

        assertThat(personByLastName, is(new HashMap<String, Person>() {{
            put("Мельников", new Person("Иван", "Мельников", 20));
            put("Доренко", new Person("Алексей", "Доренко", 40));
            put("Зимов", new Person("Николай", "Зимов", 30));
        }}));
    }

    @Test
    void createMapFromListUsingGuavaAnonymousFunction() {
        List<Person> persons = Arrays.asList(getPersons());

        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(new Function<Person, String>() {
                                                                 @Override
                                                                 public String apply(Person person) {
                                                                     return person.getLastName();
                                                                 }
                                                             });

        assertThat(personByLastName, is(new HashMap<String, Person>() {{
            put("Мельников", new Person("Иван", "Мельников", 20));
            put("Доренко", new Person("Алексей", "Доренко", 40));
            put("Зимов", new Person("Николай", "Зимов", 30));
        }}));
    }

    static Person[] getPersons() {
        return new Person[]{
                new Person("Иван", "Мельников", 20),
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30)
        };
    }
}
