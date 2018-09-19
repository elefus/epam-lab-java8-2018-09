package generics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
class Example10 {

    @Test
    void testThrowSqlException() {
//        Number list = Example10.getList();

        Number value1 = Example10.<Number>getValue();

        Object value = getValue();

        Number val = getValue();

        Example10 example10 = new Example10();
        example10.<Number>print(10);

//        Example10.<String>getList()

    }

    <T> void print(T value) {
        System.out.println(value);
    }

    private static <T extends Number> T getList() {
        return (T) null;
    }

    private static <T> T getValue() {
        return null;
    }
}
