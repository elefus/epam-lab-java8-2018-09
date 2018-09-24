package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("unused")
class Example1 {

    // String → int
    private static int strLength(String string) {
        return string.length();
    }

    @Test
    void extractStringLength() {
        Function<String, Integer> stringToLenght = Example1::strLength;

        assertThat(stringToLenght.apply("12345"), is(5));
    }

    @Test
    void extractPersonLastName() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personToLastName = Person::getLastName;

        assertThat(personToLastName.apply(person), is("Мельников"));
    }

    @Test
    void extractPersonsLastNameLength() {
        Person ivan = new Person("Иван", "Мельников", 33);

        Function<Person, Integer> personToLastNameLength = person -> person.getLastName().length();
        assertThat(personToLastNameLength.apply(ivan), is(9));
    }

    // (Person, String) → boolean
    private static boolean isSameLastName(Person person, String lastName) {
        return Objects.equals(person.getLastName(), lastName);
    }

    @Test
    void checkLastName() {
        Person ivan = new Person("Иван", "Мельников", 33);

        BiFunction<Person, String, Boolean> sameLastName = Example1::isSameLastName;
        assertThat(sameLastName.apply(ivan, "Мельников"), is(true));

        BiPredicate<Person, String> sameLastNamePredicate = Example1::isSameLastName;
        assertThat(sameLastNamePredicate.test(ivan, "Плотников"), is(false));
    }
}
