package lambda.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Example5 {

    private static <T> String extractString(T value, Function<T, String> function) {
        return function.apply(value);
    }

    private static String extract(Person person) {
        return person.getLastName();
    }

    @Test
    void extractUsingStaticMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        String lastName = extractString(person, Example5::extract);

        assertEquals("Мельников", lastName);
    }

    @Test
    void extractUsingStaticOrNotMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        String lastName = extractString(person, Person::getLastName);

        assertEquals("Мельников", lastName);
    }

    @Test
    void integerToString() {
        throw new UnsupportedOperationException();
    }
}
