package lambda.part1.exercise;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

class Exercise3 {

    @Test
    void sortPersonsByAgeUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();
        Arrays.sort(persons, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    void sortPersonsByLastNameThenFirstNameUsingArraysSortStatementLambda() {
        Person[] persons = getPersons();
        Arrays.sort(persons, (p1, p2) -> {
            int lastNameComparing = p1.getLastName().compareTo(p2.getLastName());
            int firstNameComparing = p1.getFirstName().compareTo(p2.getFirstName());
           return lastNameComparing != 0 ? lastNameComparing : firstNameComparing;
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
    void findFirstWithAge30UsingGuavaPredicateLambda() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> personOptional = FluentIterable.from(persons).firstMatch(p -> p.getAge() == 30);
        Person person = personOptional.isPresent() ? personOptional.get() : null;

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
