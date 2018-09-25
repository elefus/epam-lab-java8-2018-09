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
        //предикат Person -> boolean, проверяющий что имя и фамилия человека не пусты
        Predicate<Person> validate = person -> !person.getLastName().isEmpty() && !person.getFirstName().isEmpty();

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    // метод (Person -> boolean) -> (Person -> boolean)
    // - возвращает новый предикат, являющийся отрицанием исходного
    // - при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return (person) -> !predicate.test(person);
    }

    //  метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    //  - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    //  - при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return person -> left.test(person) && right.test(person);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().isEmpty() || person.getFirstName() == null;
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().isEmpty() || person.getFirstName() == null;

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName =
                andUsingLogicalOperator(personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }

    // метод (T -> boolean) -> (T -> boolean)
    // - возвращает новый предикат, являющийся отрицанием исходного
    // - при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {

        return t -> !predicate.test(t);
    }

    // метод (T -> boolean, T -> boolean) -> (T -> boolean)
    // - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // - при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return t -> left.test(t) && right.test(t);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().isEmpty() || person.getFirstName() == null;
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().isEmpty() || person.getFirstName() == null;

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = and(personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = person -> person.getFirstName().isEmpty() || person.getFirstName() == null;
        Predicate<Person> personHasEmptyLastName = person -> person.getLastName().isEmpty() || person.getFirstName() == null;

        //использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        //использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName = personHasNotEmptyFirstName.and(personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }
}
