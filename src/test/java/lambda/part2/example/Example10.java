package lambda.part2.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("SameParameterValue")
class Example10 {

    @FunctionalInterface
    private interface PersonFactory {

        Person create(String name, String lastName, int age);
    }

    // ((String, String, int) -> Person) -> String -> String -> int -> Person
    private static Function<String, Function<String, IntFunction<Person>>> curriedPersonFactory(PersonFactory factory) {
        return name -> lastName -> age -> factory.create(name, lastName, age);
    }

    @Test
    void testCurriedPersonFactory() {
        Person person = new Person("Иван", "Мельников", 33);

        PersonFactory factoryUsingConstructor = Person::new;
        assertThat(factoryUsingConstructor.create("Иван", "Мельников", 33), is(person));

        Function<String, Function<String, IntFunction<Person>>> curriedPersonFactory = curriedPersonFactory(Person::new);
        assertThat(curriedPersonFactory.apply("Иван").apply("Мельников").apply(33), is(person));

        Function<String, Function<String, IntFunction<Person>>> lambdaCurriedPersonFactory = lastName -> name -> age -> factoryUsingConstructor.create(name, lastName, age);
        assertThat(lambdaCurriedPersonFactory.apply("Мельников").apply("Иван").apply(33), is(person));
    }

    // ((String, String, int) -> Person, String) -> ((String, Integer) -> Person)
    private BiFunction<String, Integer, Person> personFactoryWithFixedLastName(PersonFactory factory, String lastName) {
        return (name, age) -> factory.create(name, lastName, age);
    }

    @Test
    void testPartiallyAppliedPersonFactory() {
        Person person = new Person("Иван", "Мельников", 33);

        PersonFactory factory = Person::new;

        BiFunction<String, Integer, Person> melnkikovsFactory = personFactoryWithFixedLastName(factory, "Мельников");
        assertThat(melnkikovsFactory.apply("Иван", 33), is(person));

        BiFunction<String, Integer, Person> lambdaPartyalliAppliedMelnkikovsFactory = (name, age) -> factory.create(name, "Мельников", age);
        assertThat(lambdaPartyalliAppliedMelnkikovsFactory.apply("Иван", 33), is(person));
    }

    @Test
    void testCurriedAndPartiallyAppliedPersonFactory() {
        Person person = new Person("Иван", "Мельников", 33);

        PersonFactory factory = Person::new;

        IntFunction<Function<String, Function<String, Person>>> curriedRevertedFactory = age -> lastName -> name -> factory.create(name, lastName, age);
        assertThat(curriedRevertedFactory.apply(33).apply("Мельников").apply("Иван"), is(person));

        Function<String, IntFunction<Person>> melnkikovsFactory = name -> age -> curriedRevertedFactory.apply(age).apply("Мельников").apply(name);
        assertThat(melnkikovsFactory.apply("Семён").apply(25), is(new Person("Семён", "Мельников", 25)));

        Function<String, Function<String, Person>> youngGuysFactory = lastName -> name -> curriedRevertedFactory.apply(17).apply(lastName).apply(name);
        assertThat(youngGuysFactory.apply("Игнатьев").apply("Алексей"), is(new Person("Алексей", "Игнатьев", 17)));
    }
}
