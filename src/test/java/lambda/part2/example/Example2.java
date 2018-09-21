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

    // TODO functional descriptor
    @SuppressWarnings("Convert2Lambda")
    private static Predicate<String> stringPropertyChecker(Person person, Function<Person, String> getProperty) {
        return new Predicate<String>() {
            @Override
            public boolean test(String propertyValue) {
                return Objects.equals(getProperty.apply(person), propertyValue);
            }
        };
    }

    @Test
    void checkConcretePersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO personToFirstName: Person -> String
        // TODO isFirstNameEqualsTo: (Person -> String) -> boolean

//        assertThat(isFirstNameEqualsTo.test("Иван"), is(true));
//        assertThat(isFirstNameEqualsTo.test("Игорь"), is(false));
    }

    // TODO functional descriptor
    @SuppressWarnings("Convert2Lambda")
    private static Function<Person, Predicate<String>> stringPropertyChecker(Function<Person, String> propertyExtractor) {
        return new Function<Person, Predicate<String>>() {
            @Override
            public Predicate<String> apply(Person person) {
                return new Predicate<String>() {
                    @Override
                    public boolean test(String checkingValue) {
                        return Objects.equals(propertyExtractor.apply(person), checkingValue);
                    }
                };
            }
        };
    }

    @Test
    void checkAnyPersonStringProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO personToLastNameChecker: Person -> (String -> boolean)

//        assertThat(..."Мельников");
//        assertThat(..."Гущин");
    }

    private static <V, P, T> Function<V, Predicate<P>> propertyChecker(Function<V, T> propertyExtractor) {
        return object -> checkedProperty -> Objects.equals(propertyExtractor.apply(object), checkedProperty);
    }

    @Test
    void checkAnyProperty() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO ageChecker: Person -> (Integer -> boolean)

//        assertThat(ageChecker..., is(true));
//        assertThat(ageChecker..., is(false));
    }
}
