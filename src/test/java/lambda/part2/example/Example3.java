package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"UnnecessaryLocalVariable", "CodeBlock2Expr", "ConstantConditions"})
class Example3 {

    @Test
     void extractPersonsNameLengthUsingOneFunction() {
        Person ivan = new Person("Иван", "Мельников", 33);

        // TODO personToNameLength: Person -> Integer

//        assertThat(personToNameLength.apply(ivan), is(9));
    }

    // TODO functional descriptor
    @SuppressWarnings("Convert2Lambda")
    private Function<Person, Integer> personStringPropertyToInt(Function<Person, String> personToString, Function<String, Integer> stringToInteger) {
        throw new UnsupportedOperationException();
    }

    @Test
     void extractPersonsNameLengthUsingTwoFunctions() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO personToName: Person -> String
        // TODO stringToLength: String -> Integer
        // TODO personToNameLength: Person -> Integer

//        assertThat(personToNameLength.apply(person), is(4));
    }

    // TODO functional descriptor
    private <A, B, C> Function<A, C> andThen(Function<A, B> first, Function<B, C> second) {
        return value -> second.apply(first.apply(value));
    }

    @Test
     void extractPersonsNameLengthUsingCustomAndThenFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO personToName: Person -> String
        // TODO stringToLength: String -> Integer
        // TODO personToNameLength: Person -> Integer

//        assertThat(personToNameLength.apply(person), is(9));
    }

    @Test
     void extractPersonsNameLengthUsingStandardAndThenFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO personToName: Person -> String
        // TODO stringToLength: String -> Integer
        // TODO personToNameLength: Person -> Integer

//        assertThat(personToNameLength.apply(person), is(9));
    }

    @Test
     void extractPersonsNameLengthUsingStandardComposeFunction() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO personToName: Person -> String
        // TODO stringToLength: String -> Integer
        // TODO personToNameLength: Person -> Integer

//        assertThat(personToNameLength.apply(person), is(9));
    }
}
