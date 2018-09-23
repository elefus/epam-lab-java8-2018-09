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

        Predicate<Person> validate = p -> !p.getFirstName().isEmpty() && !p.getLastName().isEmpty();

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    //  метод (Person -> boolean) -> (Person -> boolean)
    //  возвращает новый предикат, являющийся отрицанием исходного
    //  при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return p -> !predicate.test(p);
    }

    // метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    // возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return p -> left.test(p) && right.test(p);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                negateUsingLogicalOperator(andUsingLogicalOperator(personHasEmptyLastName, personHasEmptyFirstName));

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    // метод (T -> boolean) -> (T -> boolean)
    // возвращает новый предикат, являющийся отрицанием исходного
    // при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return o -> !predicate.test(o);
    }

    // метод (T -> boolean, T -> boolean) -> (T -> boolean)
    // возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return o -> left.test(o) && right.test(o);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                negate(and(personHasEmptyLastName, personHasEmptyFirstName));

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        // использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        // использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                (personHasEmptyLastName.and(personHasEmptyFirstName)).negate();

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }
}
