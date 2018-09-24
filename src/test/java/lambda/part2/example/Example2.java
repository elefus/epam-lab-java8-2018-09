package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"UnnecessaryLocalVariable", "unused"})
class Example2 {

    // (Person, Person → String) → (String → boolean)
    @SuppressWarnings("Convert2Lambda")
    private static Predicate<String> stringPropertyChecker(Person person, Function<Person, String> getProperty) {
        return propertyValue -> Objects.equals(getProperty.apply(person), propertyValue);
    }

    @Test
    void checkConcretePersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Predicate<String> isFirstNameEqualsTo = stringPropertyChecker(person, Person::getFirstName);

        assertThat(isFirstNameEqualsTo.test("Иван"), is(true));
        assertThat(isFirstNameEqualsTo.test("Игорь"), is(false));
    }

    // (Person → String) → (Person → (String → boolean))
    @SuppressWarnings("Convert2Lambda")
    private static Function<Person, Predicate<String>> stringPropertyChecker(Function<Person, String> propertyExtractor) {
        return person -> checkingValue -> Objects.equals(propertyExtractor.apply(person), checkingValue);
    }

    @Test
    void checkAnyPersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Predicate<String>> personToLastNameChecker = stringPropertyChecker(Person::getLastName);

        assertThat(personToLastNameChecker.apply(person).test("Мельников"), is(true));
        assertThat(personToLastNameChecker.apply(person).test("Гущин"), is(false));
    }

    private static <V, P, T> Function<V, Predicate<P>> propertyChecker(Function<V, T> propertyExtractor) {
        return object -> checkedProperty -> Objects.equals(propertyExtractor.apply(object), checkedProperty);
    }

    @Test
    void checkAnyProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, Predicate<String>> personToLastNameChecker = propertyChecker(Person::getLastName);

        assertThat(personToLastNameChecker.apply(person).test("Мельников"), is(true));
        assertThat(personToLastNameChecker.apply(person).test("Гущин"), is(false));
    }
}
