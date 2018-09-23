package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"unused", "ConstantConditions"})
class Exercise1 {

    @Test
    void ageExtractorFromPersonUsingMethodReference() {
        Person person = new Person("Иван", "Мельников", 33);

        //create variable ageExtractor: Person -> Integer, using Function + method-reference
        ToIntFunction<Person> ageExtractor = Person::getAge;

        assertThat(ageExtractor.applyAsInt(person), is(33));
    }

    @Test
    void sameAgesCheckerUsingBiPredicate() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Дмитрий", "Гущин", 33);
        Person person3 = new Person("Илья", "Жирков", 22);

        //create variable sameAgesChecker: (Person, Person) -> boolean, using BiPredicate
        BiPredicate<Person, Person> sameAgesChecker = (p1, p2) -> p1.getAge() == p2.getAge();

        assertThat(sameAgesChecker.test(person1, person2), is(true));
        assertThat(sameAgesChecker.test(person1, person3), is(false));
        assertThat(sameAgesChecker.test(person2, person3), is(false));
    }

    //создать метод getFullName: Person -> String, извлекающий из объекта Person строку в формате "имя фамилия".
    private static String getFullName(Person person) {
        return String.format("%s %s", person.getFirstName(), person.getLastName());
    }

    // создать метод createExtractorAgeOfPersonWithTheLongestFullName: (Person -> String) -> ((Person, Person) -> int),
    // принимающий способ извлечения полного имени из объекта Person
    // возвращающий BiFunction, сравнивающий два объекта Person и возвращающий возраст того, чье полное имя длиннее.
    private static ToIntBiFunction<Person, Person> createExtractorAgeOfPersonWithTheLongestFullName(Function<Person, String> fullNameFunction) {
        return (person1, person2) -> fullNameFunction.apply(person1).length() > fullNameFunction.apply(person2).length()
                ? person1.getAge()
                : person2.getAge();
    }

    @Test
    void getAgeOfPersonWithTheLongestFullName() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);

        // воспользоваться ссылкой на метод getFullName
        Function<Person, String> getFullName = Exercise1::getFullName;

        // (Person, Person) -> Integer
        // воспользоваться методом createExtractorAgeOfPersonWithTheLongestFullName
        ToIntBiFunction<Person, Person> extractorAgeOfPersonWithTheLongestFullName = createExtractorAgeOfPersonWithTheLongestFullName(getFullName);
        assertThat(extractorAgeOfPersonWithTheLongestFullName.applyAsInt(person1, person2), is(33));
    }

    // Just for fun
    // метод compareByString
    // принимает функцию для извлечения строки из Person
    // возвращает функцию, принимающую Predicate от Integer
    // возвращающую функцию, принимающую унарный оператор от Integer
    // возвращающую функцию, принимающую компаратор для сравнения строк
    // возвращающую бинарную функцию, принимающую два Person,
    // возвращающую true если возраст удовлетворяет предикату.
    // Integer -> (Integer -> (String -> (Person, Person) -> Boolean))
    private static Function<Predicate<Integer>,
                        Function<IntUnaryOperator,
                                Function<Comparator<String>,
                                        BiFunction<Person, Person, Boolean>>>> compareByString(Function<Person, String> getString) {
        return  predicate ->
                operator ->
                comparator ->
                (person1, person2) -> predicate.test(
                                            operator.applyAsInt(
                                                    comparator.compare(getString.apply(person1), getString.apply(person2))));
    }

    @Test
    void compareByStringTest() {
        Person person1 = new Person("Иван", "Мельников", 33);
        Person person2 = new Person("Илья", "Жирков", 22);
        boolean result = compareByString(Exercise1::getFullName)
                .apply(p -> p == 33)
                .apply(i -> i > 0 ? person1.getAge() : person2.getAge())
                .apply(Comparator.comparingInt(String::length))
                .apply(person1, person2);
        assertThat(result, is(true));
    }
}
