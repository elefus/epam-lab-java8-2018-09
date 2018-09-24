package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr", "ConstantConditions"})
class Example3 {

    @Test
     void extractPersonsNameLengthUsingOneFunction() {
        Person ivan = new Person("Иван", "Мельников", 33);

        Function<Person, Integer> personToNameLength = person -> person.getFirstName().length();

        assertThat(personToNameLength.apply(ivan), is(4));
    }

    // ((Person → String), (String → Integer)) → (Person → Integer)
    @SuppressWarnings("Convert2Lambda")
    private Function<Person, Integer> personStringPropertyToInt(
            Function<Person, String> personToString,
            Function<String, Integer> stringToInteger) {
        return person -> stringToInteger.apply(Objects.requireNonNull(personToString.apply(person)));
    }

    @Test
     void extractPersonsNameLengthUsingTwoFunctions() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personToName = Person::getFirstName;
        Function<String, Integer> stringToLength = String::length;
        Function<Person, Integer> personToNameLength = personStringPropertyToInt(personToName, stringToLength);

        assertThat(personToNameLength.apply(person), is(4));
    }

    // ((A → B), (B → C)) → (A → C)
    private <A, B, C> Function<A, C> andThen(Function<A, B> first, Function<B, C> second) {
        return value -> second.apply(first.apply(value));
    }

    @Test
     void extractPersonsNameLengthUsingCustomAndThenFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personToName = Person::getFirstName;
        Function<String, Integer> stringToLength = String::length;
        Function<Person, Integer> personToNameLength = andThen(personToName, stringToLength);

        assertThat(personToNameLength.apply(person), is(4));
    }

    @Test
     void extractPersonsNameLengthUsingStandardAndThenFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<Person, String> personToName = Person::getFirstName;
        Function<Person, Integer> personToNameLength = personToName.andThen(String::length);

        assertThat(personToNameLength.apply(person), is(4));
    }

    @Test
     void extractPersonsNameLengthUsingStandardComposeFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        Function<String, Integer> stringToLength = String::length;
        Function<Person, Integer> personToNameLength = stringToLength.compose(Person::getFirstName);

        assertThat(personToNameLength.apply(person), is(4));
    }
}
