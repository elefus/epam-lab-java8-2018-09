package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("unused")
class Example1 {

    // TODO functional descriptor
    private static int strLength(String string) {
        return string.length();
    }

    @Test
    void extractStringLength() {
        // TODO String -> Integer

//        assertThat(..., is(5));
    }

    @Test
    void extractPersonLastName() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO Person -> String

//        assertThat(..., is("Мельников"));
    }

    @Test
    void extractPersonsLastNameLength() {
        Person ivan = new Person("Иван", "Мельников", 33);

        // TODO Person -> Integer

//        assertThat(..., is(9));
    }

    // TODO functional descriptor
    private static boolean isSameLastName(Person person, String lastName) {
        return Objects.equals(person.getLastName(), lastName);
    }

    @Test
    void checkLastName() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO (Person, String) -> Boolean

//        assertThat(sameLastName...(person, "Мельников"), is(true));

        // TODO (Person, String) -> boolean

//        assertThat(sameLastNamePredicate...(person, "Плотников"), is(false));
    }
}
