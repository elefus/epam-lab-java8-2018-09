package lambda.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Person() {
        firstName = "default";
        lastName = "default";
        age = -1;
    }

    public String getFirstName(Person this) {
        return firstName;
    }

    public String getLastName(Person this) {
        return this.lastName;
    }

    public int getAge() {
        return age;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Person person = (Person) other;
        return age == person.age
            && Objects.equals(firstName, person.firstName)
            && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Person@" + hashCode() + ": {"
             + "firstName='" + firstName + "', "
             + "lastName='" + lastName + "', "
             + "age=" + age + "}";
    }
}
