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
        // TODO предикат Person -> boolean, проверяющий что имя и фамилия человека не пусты
        Predicate<Person> validate = null;

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    // TODO метод (Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        throw new UnsupportedOperationException();
    }

    // TODO метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        throw new UnsupportedOperationException();
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = null;
        Predicate<Person> personHasEmptyLastName = null;

        Predicate<Person> personHasNotEmptyFirstName = null;
        Predicate<Person> personHasNotEmptyLastName = null;

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = null;

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    // TODO метод (T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    // TODO метод (T -> boolean, T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        throw new UnsupportedOperationException();
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = null;
        Predicate<Person> personHasEmptyLastName = null;

        Predicate<Person> personHasNotEmptyFirstName = null;
        Predicate<Person> personHasNotEmptyLastName = null;

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = null;

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = null;
        Predicate<Person> personHasEmptyLastName = null;

        // TODO использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = null;
        Predicate<Person> personHasNotEmptyLastName = null;

        // TODO использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName = null;

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(true));
    }
}
