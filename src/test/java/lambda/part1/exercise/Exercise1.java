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
    void sortPersonsByAgeUsingArraysSortLocalComparator() {
        Person[] persons = getPersons();

       class PersonComparator implements Comparator<Person> {
           @Override
           public int compare(Person o1, Person o2) {
               return Integer.compare(o1.getAge(), o2.getAge());
           }
       }

       Arrays.sort(persons, new PersonComparator());

        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    @SuppressWarnings("all")
    void sortPersonsByAgeUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator <Person>() {
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
    @SuppressWarnings("all")
    void sortPersonsByLastNameThenFirstNameUsingArraysSortAnonymousComparator() {
        Person[] persons = getPersons();

        Arrays.sort(persons, new Comparator <Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                int lastNameCompare = o1.getLastName().compareTo(o2.getLastName());
                return ((lastNameCompare == 0) ? o1.getFirstName().compareTo(o2.getFirstName()) : lastNameCompare);
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
        Predicate<Person> criteria = new Predicate <Person>() {
            @Override
            public boolean apply(Person person) {
                return 30 == person.getAge();
            }
        };

        Person person = FluentIterable
                .from(persons)
                .firstMatch(criteria)
                .orNull();

        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    @Test
    @SuppressWarnings("all")
    void findFirstWithAge30UsingGuavaAnonymousPredicate() {
        List<Person> persons = Arrays.asList(getPersons());

        // TODO use FluentIterable
        Person person = FluentIterable
                .from(persons)
                .firstMatch(new Predicate <Person>() {
                    @Override
                    public boolean apply(Person person) {
                        return person.getAge() == 30;
                    }})
                .orNull();
        assertThat(person, is(new Person("Николай", "Зимов", 30)));
    }

    private Person[] getPersons() {
        return new Person[]{new Person("Иван", "Мельников", 20), new Person("Алексей", "Доренко", 40),
                            new Person("Николай", "Зимов", 30), new Person("Артем", "Зимов", 45)};
    }
}
