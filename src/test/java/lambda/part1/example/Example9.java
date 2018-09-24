package lambda.part1.example;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"unused", "ComparatorCombinators", "ConstantConditions"})
class Example9 {

    private static class FullNameComparator implements Comparator<Person>, Serializable {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    }

    @Test
    void serializeTree() {
        Set<Person> treeSet = new TreeSet<>((Comparator<Person> & Serializable)
                (o1, o2) -> o1.getFullName().compareTo(o2.getFullName()));
        treeSet.add(new Person("Иван", "Мельников", 33));
        treeSet.add(new Person("Алексей", "Игнатенко", 1));
        treeSet.add(new Person("Сергей", "Лопатин", 3));

        System.out.println(treeSet);

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(byteArray)) {
            objectOutput.writeObject(treeSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(byteArray.toByteArray()));
    }
}
