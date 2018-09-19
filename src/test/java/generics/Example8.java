package generics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Example8 {

    @Test
    void wildcardExtends() {
        List<? extends Number> list = new ArrayList<>();


    }

    @Test
    void wildcardSuper() {
        throw new UnsupportedOperationException();
    }

    @Test
    void useComparators() {
        throw new UnsupportedOperationException();
    }

    @Data
    @AllArgsConstructor
    private static class Person {

        String name;
        int age;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class Student extends Person {

        int year;

        Student(String name, int age, int year) {
            super(name, age);
            this.year = year;
        }
    }
}
