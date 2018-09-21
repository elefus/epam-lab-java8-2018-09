package lambda.part1.example;

import lambda.data.Person;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

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

        Function<Person, String> getLastName = Person::getLastName;
        Supplier<String> getFirstNameFromObject = person::getFirstName;

        assertEquals("Мельников", lastName);
    }

    @Test
    void integerToString() {

        Function<Integer, String> converter = Object::toString;
        Integer value = 42;
        Supplier<String> convertToString = value::toString;

        method(null);
    }

    void method(@NotNull Object value) {
        System.out.println(value);
    }
}
