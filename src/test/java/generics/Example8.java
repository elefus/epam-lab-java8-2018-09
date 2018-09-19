package generics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class Example8 {

    @Test
    void wildcardExtends() {
        ArrayList<Double> listDoubles = new ArrayList<>();
        List<? extends Number> list = listDoubles;

        Number number = 1;

//        list.add(number);
//        list.add(1);
//        list.add(4d);
//        list.add(4f);
//        list.add(new Object());

        list.add(null);

        Number number1 = list.get(0);
    }

    @Test
    void wildcardSuper() {
        List<? super Number> list = new ArrayList<Object>();

        Object obj = "123";
        Number num = 1;

        list.add(num);
        list.add(1);
        list.add(1d);
        list.add(1f);

        Object object = list.get(0);


        // Producer
        // Extends
        // Consumer
        // Super
    }

    @Test
    void useComparators() {
        List<Person> people = Arrays.asList(new Person("A", 1), new Person("B", 2));
        List<Student> students = Arrays.asList(new Student("A", 1, 2), new Student("B", 2, 3));

        Comparator<Object> byHashCode = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            }
        };

        Comparator<Person> sortByName = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.name.compareTo(o2.name);
            }
        };

        Comparator<Student> sortByYear = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o1.age, o2.age);
            }
        };

        sortBy(students, byHashCode);
    }

    private static void sortBy(List<? extends Student> people, Comparator<? super Student> comparator) {
        Collections.sort(people, comparator);
    }

    private static int getSize(List list) {
        List<Integer> var = new LinkedList<>();
        List raw = var;
        List<?> wild = var;

//        raw.add("123");
//        wild.add("12");

        return list.size();
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
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
