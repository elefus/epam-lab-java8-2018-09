package lambda.part1.exercise;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.arrayContaining;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise3 {

    @Test
    @SuppressWarnings("all")
    void sortPersonsByAgeUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        Arrays.sort(persons, (Person p1, Person p2) -> Integer.compare(p1.getAge(), p2.getAge()));

        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    @SuppressWarnings("all")
    void sortPersonsByLastNameThenFirstNameUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        Arrays.sort(persons, (p1, p2) -> {
            int lastNameCompare = (p1.getLastName().compareTo(p2.getLastName()));
            return ((lastNameCompare == 0) ? p1.getFirstName().compareTo(p2.getFirstName()) : lastNameCompare);
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
    void findFirstWithAge30UsingGuavaPredicateLambda() {
        List<Person> persons = Arrays.asList(getPersons());

        Predicate<Person> criteria = (p1) -> (30 == p1.getAge());

        Person person = FluentIterable.from(persons)
                .firstMatch(criteria)
                .orNull();

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
