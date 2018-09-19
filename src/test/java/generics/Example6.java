package generics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Example6 {

    @Test
    void arraysCovariant() {
        Integer[] integers = new Integer[]{1, 2, 3};
        Object[] objects = integers;

        integers[0] = 42;
        System.out.println(integers[0]);

        objects[0] = "123";
        System.out.println(objects[0]);


        Number[] numbers = {2, 3, 4};
        Integer[] values = (Integer[]) numbers;
        System.out.println(numbers[1].getClass());
        numbers[0] = 55.5;
    }

    @Test
    void genericsInvariant() {
        Integer[] values = {1, 2, 3};
        List<Integer> integers = new ArrayList<>(Arrays.asList(values));
//        List<Object> objects = integers;

        legacyCode(integers);


        Integer x = integers.get(0);
        System.out.println(x);

    }

    private void legacyCode(List<Integer> integers) {
        List objects = integers;
        objects.set(0, "123");
    }

    @Test
    void checkedCollections() {
        Integer[] values = {1, 2, 3};
        List<Integer> integers = new ArrayList<>(Arrays.asList(values));

        List<Integer> checkedList = Collections.checkedList(integers, Integer.class);

        legacyCode(checkedList);

        Integer x = integers.get(0);
        System.out.println(x);

    }
}
