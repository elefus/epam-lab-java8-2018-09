package lambda.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"unused", "Convert2MethodRef"})
class Example8 {

    @FunctionalInterface
    private interface PersonFactory {

        Person create(String name, String lastName, int age);
    }

    @Test
    void factoryAsExpressionLambda() {
        PersonFactory factory = (name, lastName, age) -> new Person(name, lastName, age);

        Person actual = factory.create("Иван", "Мельников", 33);

        assertThat(actual, is(new Person("Иван", "Мельников", 33)));
    }

    private static Person createPerson(String name, String lastName, int age) {
        return new Person(name, lastName, age);
    }

    @Test
    void factoryAsReferenceToMethod() {
        PersonFactory factory = Example8::createPerson;

        Person actual = factory.create("Иван", "Мельников", 33);

        assertThat(actual, is(new Person("Иван", "Мельников", 33)));
    }


    @Test
    void factoryAsReferenceToConstructor() {
        PersonFactory factory = Person::new;

        // TODO reference to zero-arguments constructor

        Person actual = factory.create("Иван", "Мельников", 33);

        assertThat(actual, is(new Person("Иван", "Мельников", 33)));
    }
}
