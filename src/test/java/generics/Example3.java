package generics;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
class Example3 {

    @Test
    void testNonGenericsClass() {
        NonGenericClass ref = new NonGenericClass();
        for (Integer integer : ref.getValues()) {

        }
    }

    @Test
    void testRawTypeOnGenericsClass() {
        GenericClass<Integer> ref = new GenericClass<>();
        for (Integer integer : ref.getValues()) {

        }

        GenericClass raw = new GenericClass();
        for (Object integer : raw.getValues()) {

        }

    }

    private static class NonGenericClass {

        List<Integer> getValues() {
            return Arrays.asList(1, 2, 3);
        }
    }

    private static class GenericClass<T> {

        List<Integer> getValues() {
            return Arrays.asList(1, 2, 3);
        }
    }
}
