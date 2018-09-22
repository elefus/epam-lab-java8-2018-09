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
        Predicate<Person> validate = new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return !person.getFirstName().isEmpty() && !person.getLastName().isEmpty();
            }
        };

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return !predicate.test(person);
            }
        };
    }

    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return left.test(person) && right.test(person);
            }
        };
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = negateUsingLogicalOperator(
                andUsingLogicalOperator(personHasEmptyFirstName, personHasEmptyLastName));

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return t -> !predicate.test(t);
    }

    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return t -> left.test(t) && right.test(t);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = negate(
                and(personHasEmptyFirstName, personHasEmptyLastName));

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = personHasEmptyFirstName.negate()
                .and(personHasEmptyLastName.negate());

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }
}
