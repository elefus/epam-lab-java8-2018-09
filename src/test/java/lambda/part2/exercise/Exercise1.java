package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"unused", "ConstantConditions"})
class Exercise1 {

    @Test
    void ageExtractorFromPersonUsingMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        // TODO create variable ageExtractor: Person -> Integer, using Function + method-reference

//        assertThat(ageExtractor.apply(person), is(33));

        // FIXME remove after implementation
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    void sameAgesCheckerUsingBiPredicate() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Дмитрий", "Гущин", 33);
        Person person3 = new Person("Илья", "Жирков", 22);

        // TODO create variable sameAgesChecker: (Person, Person) -> boolean, using BiPredicate

//        assertThat(sameAgesChecker.test(person1, person2), is(true));
//        assertThat(sameAgesChecker.test(person1, person3), is(false));
//        assertThat(sameAgesChecker.test(person2, person3), is(false));

        // FIXME remove after implementation
        throw new UnsupportedOperationException("Not implemented");
    }

    // TODO создать метод getFullName: Person -> String, извлекающий из объекта Person строку в формате "имя фамилия".
    // private static ... getFullName(...) {

    // TODO создать метод createExtractorAgeOfPersonWithTheLongestFullName: (Person -> String) -> ((Person, Person) -> int),
    // TODO - принимающий способ извлечения полного имени из объекта Person
    // TODO - возвращающий BiFunction, сравнивающий два объекта Person и возвращающий возраст того, чье полное имя длиннее.
    // private static ... createExtractorAgeOfPersonWithTheLongestFullName(...) {

    @Test
    void getAgeOfPersonWithTheLongestFullName() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);

        // TODO воспользоваться ссылкой на метод getFullName
        Function<Person, String> getFullName = null;

        // (Person, Person) -> Integer
        // TODO воспользоваться методом createExtractorAgeOfPersonWithTheLongestFullName
        BiFunction<Person, Person, Integer> extractorAgeOfPersonWithTheLongestFullName = null;

        assertThat(extractorAgeOfPersonWithTheLongestFullName.apply(person1, person2), is(33));
    }
}
