package lambda.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;

@SuppressWarnings({"unused", "ComparatorCombinators", "ConstantConditions"})
class Example9 {

    private static class FullNameComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    }

    @Test
    void serializeTree() {
        Set<Person> treeSet = null;
        treeSet.add(new Person("Иван", "Мельников", 33));
        treeSet.add(new Person("Алексей", "Игнатенко", 1));
        treeSet.add(new Person("Сергей", "Лопатин", 3));

        System.out.println(treeSet);

        // TODO serialize set to a byte array
    }
}
