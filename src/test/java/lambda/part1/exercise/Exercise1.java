package lambda.part1.exercise;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

class Exercise1 {

    @Test
    @SuppressWarnings("all")
    void sortPersonsByAgeUsingArraysSortLocalComparator() {
        Person[] persons = getPersons();

        class PersonComparator implements Comparator<Person> {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p1.getAge(), p2.getAge());
            }
        }
        ;

        Arrays.sort(persons, new PersonComparator());

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

        // TODO use Arrays.sort
        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(p1.getAge(), p2.getAge());
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

        // TODO use Arrays.sort
        Arrays.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                int lastNameCompareResult = p1.getLastName().compareTo(p2.getLastName());
                return lastNameCompareResult == 0 ? p1.getFirstName().compareTo(p2.getFirstName()) : lastNameCompareResult;
            }
        });

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @Test
    @SuppressWarnings("all")
    void findFirstWithAge30UsingGuavaPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO use FluentIterable
        class PersonAge30Predicate implements Predicate<Person> {
            @Override
            public boolean apply(Person person) {
                return person.getAge() == 30;
            }
        }
        ;

        Person person = FluentIterable.from(persons)
                                      .filter(new PersonAge30Predicate())
                                      .first().get();

        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    @Test
    @SuppressWarnings("all")
    void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO use FluentIterable
        Person person = FluentIterable.from(persons)
                                      .filter(new Predicate<Person>() {
                                          @Override
                                          public boolean apply(Person person) {
                                              return person.getAge() == 30;
                                          }
                                      })
                                      .first().get();

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
