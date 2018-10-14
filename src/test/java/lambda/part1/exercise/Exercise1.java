package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

class Exercise1 {

    @Test
    void sortPersonsByAgeUsingArraysSortLocalComparator() {
        Person[] persons = getPersons();

        Comparator<Person> personAgeComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        };
        Arrays.sort(persons, personAgeComparator);

        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    void sortPersonsByAgeUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Integer.compare(o1.getAge(), o2.getAge());
            }
        });

        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    void sortPersonsByLastNameThenFirstNameUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                int sortByLastName = o1.getLastName().compareTo(o2.getLastName());
                int sortByFirstName = o1.getFirstName().compareTo(o2.getFirstName());
                return sortByLastName == 0 ? sortByFirstName : sortByLastName;
            }
        });

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @SuppressWarnings("Guava")
    @Test
    void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> agePredicate = person -> Objects.equals(person.getAge(), 30);

        Optional<Person> personOptional = FluentIterable
                .from(persons)
                .firstMatch(agePredicate);

        Person person = personOptional.isPresent() ? personOptional.get() : null;

        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    @SuppressWarnings("Guava")
    @Test
    void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Person person = FluentIterable
                .from(persons)
                .firstMatch(new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return 30 == person.getAge();
            }
        }).orNull();

        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    private Person[] getPersons() {
        return new Person[]{
                new Person("Иван", "Мельников", 20),
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30),
                new Person("Артем", "Зимов", 45)
        };
    }
}
