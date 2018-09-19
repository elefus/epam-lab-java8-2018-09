package generics;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Example5 {

    @Test
    void bridgeMethod() throws NoSuchMethodException {
        Person person = new Person();

        assertThat(person.compareTo(person), is(0));

        Arrays.stream(Person.class.getDeclaredMethods()).forEach(System.out::println);
        Method compareTo = Person.class.getDeclaredMethod("compareTo", Object.class);
        System.out.println(compareTo.isSynthetic());
        System.out.println(compareTo.isBridge());
    }

    private static class Person implements Comparable<Person> {

        private String name;
        private String surname;
        private int age;

        public void method(List<String> var) {

        }

//        public void method(List<Integer> var) {
//
//        }

        @Override
        public int compareTo(Example5.Person o) {
            return 0;
        }

//        public int compareTo(Object object) {
//            return compareTo((Person)object);
//        }
    }
}
