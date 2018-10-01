package lambda.part1.exercise;

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
    void sortPersonsByAgeUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        Arrays.sort(persons, (o1, o2) -> o1.getAge() - o2.getAge());

        assertThat(persons, is(arrayContaining(
                new Person("Иван", "Мельников", 20),
                new Person("Николай", "Зимов", 30),
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45)
        )));
    }

    @Test
    void sortPersonsByLastNameThenFirstNameUsingArraysSortExpressionLambda() {
        Person[] persons = getPersons();

        Arrays.sort(persons, (o1, o2) -> {
            int compareByLastName = o1.getLastName().compareTo(o2.getLastName());
            return (compareByLastName != 0) ? compareByLastName : o1.getFirstName().compareTo(o2.getLastName());
        });

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Артем", "Зимов", 45),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @Test
    void findFirstWithAge30UsingGuavaPredicateLambda() {
        List<Person> persons = Arrays.asList(getPersons());

        Person person = FluentIterable.from(persons).firstMatch(person1 -> person1.getAge() == 30).orNull();

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
