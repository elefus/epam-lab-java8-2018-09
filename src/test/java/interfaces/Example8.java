package interfaces;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.FieldLayout;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("all")
class Example8 {

    @Test
    void stringLayout() {
        ClassLayout layout = ClassLayout.parseClass(String.class);

        int headerSize = layout.headerSize();

        SortedSet<FieldLayout> fields = layout.fields();
        int totalFieldsSize = 0;
        for (FieldLayout field : fields) {
            totalFieldsSize += field.size();
        }

        int offset = 4;
        long expectedInstanceSize = headerSize + totalFieldsSize + offset;

        assertThat(headerSize, is(12));
        assertThat(totalFieldsSize, is(8));
        assertThat(expectedInstanceSize, equalTo(layout.instanceSize()));

        System.out.println(layout.toPrintable());
    }

    @Test
    void personLayout() {
        Person ivan = new Person("Ivan", 24);
        ClassLayout layout = ClassLayout.parseInstance(ivan);

        assertThat(layout.headerSize(), is(12));
        assertThat(layout.instanceSize(), is(24L));
    }

    @Test
    void studentLayout() {
        Student ivan = new Student("Ivan", 21, 3);
        ClassLayout layout = ClassLayout.parseInstance(ivan);

        assertThat(layout.headerSize(), is(12));
        assertThat(layout.instanceSize(), is(32L));

        System.out.println(layout.toPrintable());
    }

    class Student extends Person {

        int course;

        public Student(String name, int age, int course) {
            super(name, age);
            this.course = course;
        }
    }
}

@AllArgsConstructor
class Person {

    String name;
    int age;
}


