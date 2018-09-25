package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.arrayContaining;

class Exercise1 {

    @Test
    void sortPersonsByAgeUsingArraysSortLocalComparator() {
        Person[] persons = getPersons();

        class ComparatorByAge implements Comparator<Person> {
            @Override
            public int compare(Person person1, Person person2) {
                return person1.getAge() - person2.getAge();
            }
        }

        Arrays.sort(persons, new ComparatorByAge());

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

        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                return person1.getAge() - person2.getAge();
            }
        };

        Arrays.sort(persons, personComparator);

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

        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                int i = person1.getLastName().compareTo(person2.getLastName());
                return i == 0 ? person1.getFirstName().compareTo(person2.getFirstName()) : i;
            }
        };

        Arrays.sort(persons, personComparator);

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @Test
    void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        class PersonPredicate implements Predicate<Person> {
            @Override
            public boolean apply(Person person) {
                return person.getAge() == 30;
            }
        };

        Optional<Person> personOptional = FluentIterable.from(persons).firstMatch(new PersonPredicate());
        assertThat(personOptional.isPresent(), is(true));
        assertThat(personOptional.get(), is(new Person("Николай", "Зимов", 30)));
    }

    @Test
    void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> isAge30 = new Predicate<Person>() {
            @Override
            public boolean apply(Person person) {
                return person.getAge() == 30;
            }
        };

        Optional<Person> personOptional = FluentIterable.from(persons).firstMatch(isAge30);
        assertThat(personOptional.isPresent(), is(true));
        assertThat(personOptional.get(), is(new Person("Николай", "Зимов", 30)));
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
