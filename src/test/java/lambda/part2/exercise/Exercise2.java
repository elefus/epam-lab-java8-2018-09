package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise2 {

    @Test
    void personHasNotEmptyLastNameAndFirstName() {
        Predicate<Person> validate = (p1) -> (!p1.getLastName().equals("") && !p1.getFirstName().equals(""));

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return person -> !predicate.test(person);
    }

    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return person -> left.test(person) && right.test(person);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().equals("");
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().equals("");

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                negateUsingLogicalOperator(andUsingLogicalOperator(personHasEmptyFirstName, personHasEmptyLastName));

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return (T) -> !predicate.test(T);
    }

    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return T -> left.test(T) && right.test(T);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().equals("");
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().equals("");

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                negate(and(personHasEmptyFirstName, personHasEmptyLastName));

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().equals("");
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().equals("");

        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = personHasEmptyLastName.and(personHasEmptyFirstName).negate();

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }
}