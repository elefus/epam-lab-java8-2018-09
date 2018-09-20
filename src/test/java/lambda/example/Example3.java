package lambda.example;

import com.google.common.base.Optional;
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
class Example3 {

    @Test
    void sortPersonsByLastNameUsingArraysLambda() {
        Person[] persons = getPersons();

        Arrays.sort(persons, (p1, p2) -> p1.getLastName().compareTo(p2.getLastName()));
        Arrays.sort(persons, Comparator.comparing(person -> person.getLastName()));
        Arrays.sort(persons, Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName));

//        (Person p1, Person p2) -> p1.getLastName().compareTo(p2.getLastName());

        assertThat(persons, is(arrayContaining(
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30),
                new Person("Иван", "Мельников", 20)
        )));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void findFirstAlexUsingForeach() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> result = FluentIterable.from(persons)
                                                .firstMatch(person -> "Алексей".equals(person.getFirstName()));

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(new Person("Алексей", "Доренко", 40)));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void personsBylastNames() {
        List<Person> persons = Arrays.asList(getPersons());

        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(Person::getLastName);

        assertThat(personByLastName, is(new HashMap<String, Person>() {{
            put("Мельников", new Person("Иван", "Мельников", 20));
            put("Доренко", new Person("Алексей", "Доренко", 40));
            put("Зимов", new Person("Николай", "Зимов", 30));
        }}));
    }

    private Person[] getPersons() {
        return new Person[]{
                new Person("Иван", "Мельников", 20),
                new Person("Алексей", "Доренко", 40),
                new Person("Николай", "Зимов", 30)
        };
    }
}
